package com.api.ecommerce.domain.models;

import com.api.ecommerce.domain.enums.UserRole;

public record User(
        Long id,
        String name,
        String email,
        String password,
        UserRole role
) {
    public UserBuilder copy() {
        return new UserBuilder(this);
    }
    public static class UserBuilder {
        private Long id;
        private String name;
        private String email;
        private String password;
        private UserRole role;

        public UserBuilder(User user) {
            this.id = user.id;
            this.name = user.name;
            this.email = user.email;
            this.password = user.password;
            this.role = user.role;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(id, name, email, password, role);
        }

    }
}
