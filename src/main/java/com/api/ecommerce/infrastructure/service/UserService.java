package com.api.ecommerce.infrastructure.service;

import com.api.ecommerce.application.ports.in.service.UserUC;
import com.api.ecommerce.application.ports.out.repository.UserRepository;
import com.api.ecommerce.domain.models.User;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.RegisterRequest;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginResponse;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginRequest;
import com.api.ecommerce.infrastructure.entity.UserEntity;
import com.api.ecommerce.infrastructure.mapper.UserMapper;
import com.api.ecommerce.infrastructure.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserUC {

    private final UserRepository userRepositoryPort;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepositoryPort, AuthenticationManager authenticationManager, TokenService tokenService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest data) {
        User newUser = userMapper.toUser(data).copy()
                .password(passwordEncoder.encode(data.password())).build();

        existEmail(newUser.email());

        userRepositoryPort.save(newUser);
    }

    @Override
    public LoginResponse loginUser(LoginRequest data) {
        Authentication authenticatedUser = this.authenticationUser(data);
        final String token = tokenService.generateToken((UserEntity) authenticatedUser.getPrincipal());
        return new LoginResponse(token);
    }

    private Authentication authenticationUser(LoginRequest data) {
        final UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        return this.authenticationManager.authenticate(usernamePassword);
    }

    private void existEmail(String email) {
        if (userRepositoryPort.existsByEmail(email)) {
            throw new IllegalArgumentException("Usuário com este e-mail já existe.");
        }
    }

}
