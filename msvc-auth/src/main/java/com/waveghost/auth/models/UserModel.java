package com.waveghost.auth.models;

import com.waveghost.auth.infrastructure.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserModel {

    private String id;

    private String email;
    private String password;
    
    private UserRole role;
}