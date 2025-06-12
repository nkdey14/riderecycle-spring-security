package com.riderecycle.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
                throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7, token.length()); // Remove "Bearer " prefix
            System.out.println("Processed token: " + jwtToken);
            // Here you would typically validate the token and set the authentication in the security context
        } else {
            System.out.println("No valid token found in request header.");
        }
    }
}
