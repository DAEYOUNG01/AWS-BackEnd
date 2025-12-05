package com.bookbackend.backend.user.service;

import com.bookbackend.backend.user.dto.UserLoginRequest;
import com.bookbackend.backend.user.dto.UserResponse;
import com.bookbackend.backend.user.dto.UserSignupRequest;
import com.bookbackend.backend.user.entity.User;
import com.bookbackend.backend.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Lombok 없이 직접 생성자 정의
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =======================
    // 회원가입
    // =======================
    public UserResponse signup(UserSignupRequest request) {

        // 1) 빈 값 체크 -> 402
        if (isBlank(request.getUser_id()) ||
                isBlank(request.getUser_password()) ||
                isBlank(request.getName())) {
            throw new IllegalArgumentException("EMPTY");
        }

        // 2) 아이디 중복 체크 -> 401
        if (userRepository.existsByUserId(request.getUser_id())) {
            throw new IllegalStateException("DUPLICATE_ID");
        }

        // 3) 비밀번호 암호화
        String encodedPw = passwordEncoder.encode(request.getUser_password());

        // 4) 엔티티 생성 & 저장
        User user = new User(
                request.getUser_id(),
                encodedPw,
                request.getName()
        );

        User saved = userRepository.save(user);

        // 5) 응답 DTO 생성
        return new UserResponse(
                saved.getId(),
                saved.getUserId(),
                saved.getUserPassword(),
                saved.getName()
        );
    }

    // =======================
    // 로그인
    // =======================
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

    // 공통 공백 체크 유틸
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
