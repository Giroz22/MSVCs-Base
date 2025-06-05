package com.waveghost.auth.api.dtos.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;

public record RegisterRequest(
    @Email(message = "Email invalid")
    String email, 

    @Length(min = 8, message = "Password must be min 8 characters")
    String password
) {
    
}
