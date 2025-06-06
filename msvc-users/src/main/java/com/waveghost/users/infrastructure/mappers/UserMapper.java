package com.waveghost.users.infrastructure.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.waveghost.users.api.dtos.request.UserRequest;
import com.waveghost.users.api.dtos.response.UserResponse;
import com.waveghost.users.persistence.entitites.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "role", ignore = true)
    })
    UserEntity ToUserEntity(UserRequest request);

    UserResponse ToDto(UserEntity entity);
    List<UserResponse> ToDto(List<UserEntity> entity);

    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void copy(UserEntity source, @MappingTarget UserEntity target);
}
