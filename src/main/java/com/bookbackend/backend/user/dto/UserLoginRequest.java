package com.bookbackend.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    private String user_id;
    private String user_password;
}
