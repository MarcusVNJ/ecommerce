package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.UserUC;
import com.api.ecommerce.infrastructure.controller.resource.AuthResource;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController implements AuthResource {

    private final UserUC userUseCase;

    public RegisterController(UserUC userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> execute(@RequestBody @Valid RegisterRequest data) {
        userUseCase.registerUser(data);
        return ResponseEntity.status(201).build();
    }

}
