package com.bookbackend.backend.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 실제 테이블명에 맞춰서 변경 (user / User 등)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 uuid (PK)

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId; // 로그인용 아이디

    @Column(name = "user_password", nullable = false, length = 100)
    private String userPassword; // 비밀번호(암호화 저장 권장)

    @Column(name = "name", nullable = false, length = 20)
    private String name; // 사용자 이름
}
