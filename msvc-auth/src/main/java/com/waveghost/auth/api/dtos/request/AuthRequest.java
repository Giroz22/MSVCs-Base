package com.waveghost.auth.api.dtos.request;

import jakarta.validation.constraints.Email;

public record AuthRequest(
    @Email(message = "Email invalid")
    String email, 

    String password
) {}
