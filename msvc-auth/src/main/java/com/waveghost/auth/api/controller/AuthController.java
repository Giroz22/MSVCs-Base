package com.waveghost.auth.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waveghost.auth.api.dtos.request.AuthRequest;
import com.waveghost.auth.api.dtos.request.RegisterRequest;
import com.waveghost.auth.api.dtos.response.AuthResponse;
import com.waveghost.auth.domain.abstract_services.IAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(
            new AuthResponse(
                this.authService.register(request)
            )
        );
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(
            new AuthResponse(
                this.authService.login(request)
            )
        );
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello Everyone Authenticated!!");
    }
    
}
