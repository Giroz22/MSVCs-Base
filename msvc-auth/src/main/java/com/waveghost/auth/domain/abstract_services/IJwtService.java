package com.waveghost.auth.domain.abstract_services;

import java.util.List;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.waveghost.auth.models.UserModel;

public interface IJwtService {
    String generateToken(UserModel userEntity);
    DecodedJWT validateToken(String token);
    String getUsername(DecodedJWT decodedJWT);
    List<String> getAuthorities(DecodedJWT decodedJWT);
}
