package com.api.ecommerce.infrastructure.repository;

import com.api.ecommerce.application.ports.out.repository.UserRepository;
import com.api.ecommerce.domain.models.User;
import com.api.ecommerce.infrastructure.entity.UserEntity;
import com.api.ecommerce.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    public final SpringUserRepository userRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(SpringUserRepository userCrudRepository, UserMapper userMapper) {
        this.userRepository = userCrudRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        userEntity = userRepository.save(userEntity);
        return userMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userMapper::toUser);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
