package com.waveghost.users.api.dtos.request;

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
public class RoleRequest {
    private UserRole role;
}
