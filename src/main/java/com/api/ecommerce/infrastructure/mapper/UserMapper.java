package com.api.ecommerce.infrastructure.mapper;

import com.api.ecommerce.domain.models.User;
import com.api.ecommerce.infrastructure.dto.AuthDTOs.RegisterRequest;
import com.api.ecommerce.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings(
            {
                    @Mapping(source = "id", target = "id"),
                    @Mapping(source = "name", target = "name"),
                    @Mapping(source = "email", target = "email"),
                    @Mapping(source = "password", target = "password"),
                    @Mapping(source = "role", target = "role"),

            }
    )
    User toUser(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    User toUser(RegisterRequest user);

    Iterable<User> toUsers( Iterable<UserEntity> userEntities);

    @InheritInverseConfiguration
    UserEntity toUserEntity(User user);

}
