package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.application.ports.in.service.UserService;
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
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> execute(@RequestBody @Valid LoginRequest data) {
        return ResponseEntity.ok(userService.loginUser(data));
    }

}
