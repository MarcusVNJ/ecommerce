package com.api.ecommerce.application.ports.in;

import com.api.ecommerce.domain.models.User;

public interface UserUC {
    User registerUser(User user);
}
