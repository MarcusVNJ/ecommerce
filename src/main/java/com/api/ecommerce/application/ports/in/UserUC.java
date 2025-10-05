package com.api.ecommerce.application.ports.in;

import com.api.ecommerce.infrastructure.dto.AuthDTOs.RegisterRequest;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginRequest;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.LoginResponse;


public interface UserUC {
    void registerUser(RegisterRequest data);
    LoginResponse loginUser(LoginRequest data);
}
