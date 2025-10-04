package com.api.ecommerce.application.ports.in.service;

import com.api.ecommerce.application.ports.in.UserUC;
import com.api.ecommerce.application.ports.out.repository.UserRepository;
import com.api.ecommerce.domain.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserUC {

    private final UserRepository userRepositoryPort;

    public UserService(UserRepository userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    @Transactional
    public User registerUser(User user) {
        if (userRepositoryPort.existsByEmail(user.email())) {
            throw new IllegalArgumentException("Usuário com este e-mail já existe.");
        }
        return userRepositoryPort.save(user);
    }
}
