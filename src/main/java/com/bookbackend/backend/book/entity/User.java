package com.bookbackend.backend.book.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")   // ★ 여기 추가!
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String user_id;

    @Column(length = 50, nullable = false)
    private String user_password;

    @Column(length = 50, nullable = false)
    private String name;
}
