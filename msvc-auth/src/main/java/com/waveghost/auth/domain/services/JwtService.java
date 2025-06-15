package com.waveghost.auth.domain.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.waveghost.auth.api.clients.UserClient;
import com.waveghost.auth.domain.abstract_services.IJwtService;
import com.waveghost.auth.infrastructure.errors.UsernameNotFoundException;
import com.waveghost.auth.models.UserModel;

@Service
public class JwtService implements IJwtService {
    private final String SECRET_KEY = "temporal123";
    private final String GENERATOR = "BACKEND-TEMPORAL";

    @Autowired
    private UserClient userClient;

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
            .withClaim("id", userEntity.getId())
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
            .withJWTId(UUID.randomUUID().toString())
            .sign(algorithm);
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.GENERATOR)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            String username = this.getUsername(decodedJWT);
            Boolean existUsername = this.userClient.emailExist(username).getBody();

            if (!existUsername) 
                throw new UsernameNotFoundException("User with this username not was found");

            return decodedJWT;

        } catch (Exception e) {
            throw new JWTVerificationException(e.getMessage());
        }
    }

    public Claim getClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public String getUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public List<String> getAuthorities(DecodedJWT decodedJWT){
        List<String> authorities = decodedJWT.getClaim("authorities").asList(String.class);

        if (authorities == null) {
            String authoritiesString = decodedJWT.getClaim("authorities").asString();

            if (authoritiesString != null && !authoritiesString.isEmpty()) 
                return Arrays.asList(authoritiesString.split(","));
        }

        return authorities != null ? authorities : new ArrayList<>();
    }
}
