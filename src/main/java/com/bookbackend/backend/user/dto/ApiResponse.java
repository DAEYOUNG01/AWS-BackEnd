package com.bookbackend.backend.user.dto;

public class ApiResponse<T> {

    private String status;
    private String message;
    private T data;

    // 생성자
    public ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // getter 들
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public T getData() { return data; }

    // 필요하면 setter 도 추가
}
