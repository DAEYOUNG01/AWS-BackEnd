package com.bookbackend.backend.user.service;

import com.bookbackend.backend.config.JWTProvider;
import com.bookbackend.backend.user.dto.*;
import com.bookbackend.backend.user.entity.User;
import com.bookbackend.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    // ------------------------------
    // 🔹 회원가입
    // ------------------------------
    public JWTResponse signup(SignUpRequest request) {

        // 1. 빈 값 체크
        if (isBlank(request.getLoginId()) ||
                isBlank(request.getPassword()) ||
                isBlank(request.getName())) {
            throw new IllegalArgumentException("EMPTY");
        }

        // 2. 중복 아이디 체크
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalStateException("DUPLICATE_ID");
        }

        // 3. 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(request.getPassword());

        // 4. 엔티티 생성
        User user = User.builder()
                .loginId(request.getLoginId())
                .password(encodedPw)
                .name(request.getName())
                .build();

        User saved = userRepository.save(user);

        // 5. JWT 발급
        String access = jwtProvider.generateAccessToken(saved.getLoginId());
        String refresh = jwtProvider.generateRefreshToken(saved.getLoginId());

        return new JWTResponse(
                access,
                refresh,
                jwtProvider.getAccessTokenExpiry()
        );
    }

    // ------------------------------
    // 🔹 로그인
    // ------------------------------
    public JWTResponse login(LoginRequset request) {

        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("LOGIN_FAIL"));

        // 비밀번호 일치 검사
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("LOGIN_FAIL");
        }

        // JWT 발급
        String access = jwtProvider.generateAccessToken(user.getLoginId());
        String refresh = jwtProvider.generateRefreshToken(user.getLoginId());

        return new JWTResponse(
                access,
                refresh,
                jwtProvider.getAccessTokenExpiry()
        );
    }

    // ------------------------------
    // 🔹 회원 정보 수정
    // ------------------------------
    public UpdateUserResponse updateUser(UpdateUserRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("NOT_FOUND_USER"));

        // loginId 변경
        if (!isBlank(request.getLoginId())) {

            // 중복 아이디 체크 (자기 자신 제외)
            if (userRepository.existsByLoginId(request.getLoginId()) &&
                    !request.getLoginId().equals(user.getLoginId())) {

                throw new IllegalStateException("DUPLICATE_ID");
            }

            user.setLoginId(request.getLoginId());
        }

        // 비밀번호 변경
        if (!isBlank(request.getPassword())) {
            String encodedPw = passwordEncoder.encode(request.getPassword());
            user.setPassword(encodedPw);
        }

        // 이름 변경
        if (!isBlank(request.getName())) {
            user.setName(request.getName());
        }

        User updated = userRepository.save(user);

        return new UpdateUserResponse(
                updated.getUserId(),
                updated.getLoginId(),
                updated.getName()
        );
    }

    // ------------------------------
    // 🔹 회원 탈퇴
    // ------------------------------
    public ResignResponse resign(ResignRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("NOT_FOUND_USER"));

        // 탈퇴 시 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("INVALID_PASSWORD");
        }

        userRepository.delete(user);

        return new ResignResponse("회원 탈퇴 완료");
    }

    // ------------------------------
    // 🔹 내부 공용 유틸 함수
    // ------------------------------
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
