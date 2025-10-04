package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.UserUC;
import com.api.ecommerce.domain.models.User;
import com.api.ecommerce.infrastructure.controller.resource.AuthResource;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.RegisterRequest;
import com.api.ecommerce.infrastructure.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController implements AuthResource {
    private final UserMapper userMapper;
    private final UserUC userUseCase;
    private final PasswordEncoder passwordEncoder;

    public RegisterController(UserMapper userMapper, UserUC userUseCase, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userUseCase = userUseCase;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> execute(@RequestBody @Valid RegisterRequest data) {

        User newUser = userMapper.toUser(data).withPasswordEncode(passwordEncoder.encode(data.password()));

        userUseCase.registerUser(newUser);

        return ResponseEntity.status(201).build();
    }

}
