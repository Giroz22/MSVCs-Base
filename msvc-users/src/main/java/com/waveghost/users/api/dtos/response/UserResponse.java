package com.waveghost.users.api.dtos.response;

import com.waveghost.users.infrastructure.enums.UserRole;

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
public class UserResponse {
    private String id;
    private String email;
    private String password;
    private UserRole role;
}
