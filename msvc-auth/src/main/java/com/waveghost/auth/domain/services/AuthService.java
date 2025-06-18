package com.waveghost.auth.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waveghost.auth.api.clients.UserClient;
import com.waveghost.auth.api.dtos.request.AuthRequest;
import com.waveghost.auth.api.dtos.request.RegisterRequest;
import com.waveghost.auth.api.dtos.request.UserRequest;
import com.waveghost.auth.domain.abstract_services.IAuthService;
import com.waveghost.auth.infrastructure.enums.UserRole;
import com.waveghost.auth.infrastructure.errors.BadCredentialsException;
import com.waveghost.auth.infrastructure.errors.EmailAlreadyExistException;
import com.waveghost.auth.models.UserModel;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        Boolean emailExist = this.userClient.emailExist(request.email()).getBody();

        if (emailExist) {
            throw new EmailAlreadyExistException(request.email());
        }

        UserRequest userRequest = UserRequest.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .roles(List.of(UserRole.USER))
            .build();
        
        UserModel userSaved = this.userClient.create(userRequest).getBody();

        return jwtService.generateToken(userSaved);
    }

    @Override
    public String login(AuthRequest request) {
        UserModel userEntity = this.userClient.getByEmail(request.email()).getBody();

        if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtService.generateToken(userEntity);
    }
}
