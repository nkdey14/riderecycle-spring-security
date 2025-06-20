package com.riderecycle.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class JWTService {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.expiry-time}")
    private long expiry; // changed from String to long

    @Value("${jwt.issuer}")
    private String issuer;

    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(key);
    }

    public String generateToken(String username) {
        return JWT.create()
                .withClaim("name", username)
                .withIssuer(issuer)
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + expiry))
                .sign(algorithm);
    }

    public String getUsername(String token){
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim("name")
                .asString();
    }

}
