package com.api.ecommerce.application.ports.out.repository;

import com.api.ecommerce.domain.models.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
