package com.waveghost.auth.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waveghost.auth.api.dtos.request.AuthRequest;
import com.waveghost.auth.api.dtos.request.RegisterRequest;
import com.waveghost.auth.domain.abstract_services.IAuthService;
import com.waveghost.auth.infrastructure.enums.UserRole;
import com.waveghost.auth.infrastructure.errors.BadCredentialsException;
import com.waveghost.auth.infrastructure.errors.UsernameNotFoundException;
import com.waveghost.auth.persistence.entitites.UserEntity;
import com.waveghost.auth.persistence.repositories.UserRepository;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {
        UserEntity userEntity = UserEntity.builder()
            .username(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(UserRole.USER)
            .build();
        
        this.userRepository.save(userEntity);

        return jwtService.generateToken(userEntity);
    }

    @Override
    public String login(AuthRequest request) {
        UserEntity userEntity = this.userRepository.findByUsername(request.email())
                                    .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtService.generateToken(userEntity);
    }
}
