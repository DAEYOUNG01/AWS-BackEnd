package com.bookbackend.backend.user.controller;

import com.bookbackend.backend.user.dto.*;
import com.bookbackend.backend.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class UserController {

    private final UserService userService;

    // 🔽 직접 생성자 작성
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userinsert")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest request) {
        try {
            UserResponse user = userService.signup(request);

            ApiResponse<UserResponse> body =
                    new ApiResponse<>("success", "회원가입 성공", user);

            return ResponseEntity.status(HttpStatus.OK).body(body);

        } catch (IllegalStateException e) { // DUPLICATE_ID
            ApiResponse<Void> body =
                    new ApiResponse<>("error", "이미 존재하는 아이디 입니다.", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body); // 401

        } catch (IllegalArgumentException e) {
            if ("EMPTY".equals(e.getMessage())) {
                ApiResponse<Void> body =
                        new ApiResponse<>("error", "빈 칸이 존재합니다.", null);
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(body); // 402
            }
            ApiResponse<Void> body =
                    new ApiResponse<>("error", "잘못된 요청입니다.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            UserResponse user = userService.login(request);

            ApiResponse<UserResponse> body =
                    new ApiResponse<>("success", "로그인 성공", user);

            return ResponseEntity.ok(body);

        } catch (IllegalArgumentException e) { // LOGIN_FAIL
            ApiResponse<Void> body =
                    new ApiResponse<>("error", "아이디 또는 비밀번호가 틀렸습니다.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400
        }
    }
}
