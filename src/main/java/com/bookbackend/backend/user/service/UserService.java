package com.bookbackend.backend.user.service;

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

    // 회원가입
    public UserResponse signup(UserSignupRequest request) {

        // 1) 빈 값 체크 → 402
        if (isBlank(request.getUser_id()) ||
                isBlank(request.getUser_password()) ||
                isBlank(request.getName())) {
            throw new IllegalArgumentException("EMPTY"); // 나중에 컨트롤러에서 코드 매핑
        }

        // 2) 아이디 중복 체크 → 401
        if (userRepository.existsByUserId(request.getUser_id())) {
            throw new IllegalStateException("DUPLICATE_ID");
        }

        // 3) 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(request.getUser_password());

        // JW 토큰
        // 추가 요구사항  - pull requ

        // 4) 엔티티 생성 & 저장
        User user = User.builder()
                .userId(request.getUser_id())
                .userPassword(encodedPw)
                .name(request.getName())
                .build();

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getUserPassword(),
                saved.getName()
        );
    }

    // 로그인
    public UserResponse login(UserLoginRequest request) {

        // 아이디 존재 여부 체크
        User user = userRepository.findByUserId(request.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("LOGIN_FAIL"));

        // 패스워드 검증
        if (!passwordEncoder.matches(request.getUser_password(), user.getUserPassword())) {
            throw new IllegalArgumentException("LOGIN_FAIL");
        }

        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getUserPassword(),
                user.getName()
        );
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
