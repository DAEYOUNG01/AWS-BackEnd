package com.bookbackend.backend.user.dto;

public class UserResponse {

    private Long id;
    private String userId;
    private String userPassword;
    private String name;

    // 🔹 UserService에서 쓰는 4개 파라미터 생성자
    public UserResponse(Long id, String userId, String userPassword, String name) {
        this.id = id;
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
    }

    // ===== getter / setter =====
    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setName(String name) {
        this.name = name;
    }
}
