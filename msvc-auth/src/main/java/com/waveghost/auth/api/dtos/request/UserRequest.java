package com.waveghost.auth.api.dtos.request;

import java.util.List;

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
public class UserRequest {
    private String email;
    private String password;
    private List<UserRole> roles;
}
