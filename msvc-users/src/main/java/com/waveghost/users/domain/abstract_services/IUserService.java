package com.waveghost.users.domain.abstract_services;

import java.util.List;

import com.waveghost.users.domain.abstract_services.crud.ICreate;
import com.waveghost.users.domain.abstract_services.crud.IDelete;
import com.waveghost.users.domain.abstract_services.crud.IGetAll;
import com.waveghost.users.domain.abstract_services.crud.IGetById;
import com.waveghost.users.domain.abstract_services.crud.IUpdate;
import com.waveghost.users.infrastructure.enums.UserRole;
import com.waveghost.users.persistence.entitites.UserEntity;

public interface IUserService extends 
    ICreate<UserEntity>,
    IGetAll<UserEntity>,
    IGetById<UserEntity, String>,
    IUpdate<UserEntity, String>,
    IDelete<String>

{
    UserEntity create(UserEntity entity, List<UserRole> roles);
    UserEntity findByEmail(String email);
    boolean emailExist(String email);
}
