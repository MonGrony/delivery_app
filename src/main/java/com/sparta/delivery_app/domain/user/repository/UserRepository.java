package com.sparta.delivery_app.domain.user.repository;

import com.sparta.delivery_app.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long userId);

    Page<User> findAll(Pageable pageable);
}
