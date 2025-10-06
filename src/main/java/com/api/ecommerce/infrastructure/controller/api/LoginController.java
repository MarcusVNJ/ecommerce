package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.UserUC;
import com.api.ecommerce.infrastructure.controller.resource.AuthResource;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginResponse;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements AuthResource {
    private final UserUC userUseCase;

    public LoginController(UserUC userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> execute(@RequestBody @Valid LoginRequest data) {
        return ResponseEntity.ok(userUseCase.loginUser(data));
    }

}
