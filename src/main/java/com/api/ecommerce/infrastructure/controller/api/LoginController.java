package com.api.ecommerce.infrastructure.controller.api;

import com.api.ecommerce.infrastructure.controller.resource.AuthResource;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginResponse;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginRequest;
import com.api.ecommerce.infrastructure.entity.UserEntity;
import com.api.ecommerce.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements AuthResource {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> execute(@RequestBody @Valid LoginRequest data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userEntity = (UserEntity) auth.getPrincipal();
        var token = tokenService.generateToken(userEntity);

        return ResponseEntity.ok(new LoginResponse(token));
    }

}
