package com.bookbackend.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String user_id;
    private String user_password; // 실제 서비스라면 보통 안 보낸다
    private String name;
}
