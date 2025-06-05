package com.waveghost.auth.domain.abstract_services;

import com.waveghost.auth.api.dtos.request.AuthRequest;
import com.waveghost.auth.api.dtos.request.RegisterRequest;

public interface IAuthService {
    String register(RegisterRequest request);
    String login(AuthRequest request);
}
