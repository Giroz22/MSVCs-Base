package com.waveghost.users.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waveghost.users.persistence.entitites.RoleEntity;
import java.util.Optional;

import com.waveghost.users.infrastructure.enums.UserRole;


public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByRole(UserRole role);
}
