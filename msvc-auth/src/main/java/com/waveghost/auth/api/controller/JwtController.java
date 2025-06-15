package com.waveghost.auth.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.waveghost.auth.domain.abstract_services.IJwtService;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    private IJwtService jwtService;

    @GetMapping("/validate-token")
    public ResponseEntity<DecodedJWT> validateToken(@RequestParam String token) {
        return ResponseEntity.ok(
            this.jwtService.validateToken(token)
        );
    }

    @GetMapping("/get-username")
    public ResponseEntity<String> getUsername(@RequestParam String token) {
        DecodedJWT decodedJWT = this.jwtService.validateToken(token);

        return ResponseEntity.ok(
            this.jwtService.getUsername(decodedJWT)
        );
    }
    
    @GetMapping("/get-authorities")
    public ResponseEntity<List<String>> getAuthorities(@RequestParam String token) {
        DecodedJWT decodedJWT = this.jwtService.validateToken(token);

        return ResponseEntity.ok(
            this.jwtService.getAuthorities(decodedJWT)
        );
    }
}
