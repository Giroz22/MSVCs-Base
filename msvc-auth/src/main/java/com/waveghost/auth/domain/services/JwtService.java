package com.waveghost.auth.domain.services;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.waveghost.auth.models.UserModel;

@Service
public class JwtService {
    private final String SECRET_KEY = "temporal123";
    private final String GENERATOR = "BACKEND-TEMPORAL";

    public String generateToken(UserModel userEntity){

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String authorities = userEntity.getRoles()
            .stream()
            .map(role -> "ROLE_".concat(role.getRole().name()))
            .collect(Collectors.joining(","));

        return JWT.create()
            .withIssuer(GENERATOR)
            .withSubject(userEntity.getEmail())
            .withClaim("authorities", authorities)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
            .withJWTId(UUID.randomUUID().toString())
            .sign(algorithm);
    }

    public Claim getClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    
}
