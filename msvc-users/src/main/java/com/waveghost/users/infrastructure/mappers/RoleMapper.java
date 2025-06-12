package com.waveghost.users.infrastructure.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.waveghost.users.api.dtos.request.RoleRequest;
import com.waveghost.users.persistence.entitites.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    RoleEntity ToRoleEntity(RoleRequest request);

    RoleRequest ToDto(RoleEntity entity);
    List<RoleRequest> ToDto(List<RoleEntity> entity);

    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void copy(RoleEntity source, @MappingTarget RoleEntity target);
}
