package com.bookbackend.backend.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // H2 예약어 피해서 users
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "user_password", nullable = false, length = 100)
    private String userPassword;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    // JPA 기본 생성자
    public User() {
    }

    // 서비스에서 사용할 생성자
    public User(String userId, String userPassword, String name) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
    }

    // getters / setters
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
