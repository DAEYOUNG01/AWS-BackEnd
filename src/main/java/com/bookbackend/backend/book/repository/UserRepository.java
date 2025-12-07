package com.bookbackend.backend.book.repository;

import com.bookbackend.backend.book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
