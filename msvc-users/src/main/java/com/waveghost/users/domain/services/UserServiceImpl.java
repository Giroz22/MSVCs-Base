package com.waveghost.users.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waveghost.users.domain.abstract_services.IUserService;
import com.waveghost.users.infrastructure.enums.UserRole;
import com.waveghost.users.infrastructure.exceptions.NotFoundException;
import com.waveghost.users.infrastructure.mappers.UserMapper;
import com.waveghost.users.persistence.entitites.UserEntity;
import com.waveghost.users.persistence.repositories.UserRepository;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserEntity create(UserEntity entity) {
        entity.setRole(UserRole.USER);
        return this.userRepository.save(entity);
    }

    @Override
    public List<UserEntity> getAll() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity getById(String id) {
        return this.userRepository.findById(id).orElseThrow(
            () -> new NotFoundException("The user with ID: "+ id +", was not found.")
        );
    }

    @Override
    @Transactional
    public UserEntity update(String id, UserEntity entityUpdate) {
        UserEntity entity = this.getById(id);
        this.userMapper.copy(entityUpdate, entity);

        return this.userRepository.save(entity);
    }

    @Override
    @Transactional
    public void delete(String id) {
        UserEntity userDelete = this.getById(id);

        this.userRepository.delete(userDelete);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(
            () -> new NotFoundException("The email: " + email + ", was not found.")
        );
    }

    @Override
    public boolean emailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
    
}
