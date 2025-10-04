package com.api.ecommerce.domain.models;

import com.api.ecommerce.domain.enums.UserRole;

public record User(
        Long id,
        String name,
        String email,
        String password,
        UserRole role
) {
    public User withPasswordEncode(String newPassword) {
        return new User(this.id, this.name, this.email, newPassword, this.role);
    }
}
