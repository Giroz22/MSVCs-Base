package com.waveghost.gateway.infrastructure.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "temporal123";
    private final String GENERATOR = "BACKEND-TEMPORAL";

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.GENERATOR)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT;

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token invalid, not authorized");
        }
    }

    public Claim getClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    public List<String> getAuthorities(DecodedJWT decodedJWT){
        List<String> authorities = decodedJWT.getClaim("authorities").asList(String.class);
        if (authorities == null) {
            String authoritiesString = decodedJWT.getClaim("authorities").asString();
            if (authoritiesString != null && !authoritiesString.isEmpty()) {
                return Arrays.asList(authoritiesString.split(","));
            }
        }
        return authorities != null ? authorities : new ArrayList<>();
    }

}