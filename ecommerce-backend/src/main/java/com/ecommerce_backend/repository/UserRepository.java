package com.ecommerce_backend.repository;

import com.ecommerce_backend.entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Nullable User findByusername(String username);

    User findbyUserId(Long userId);
}