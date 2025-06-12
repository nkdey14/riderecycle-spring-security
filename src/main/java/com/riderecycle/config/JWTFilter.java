package com.riderecycle.config;

import com.riderecycle.service.impl.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JWTFilter extends OncePerRequestFilter{

    private JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal
            (
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {
       String token = request.getHeader("Authorization");
       if(token != null && token.startsWith("Bearer ")){
            String jwtToken = token.substring(7, token.length());
            String username = jwtService.getUsername(jwtToken);
            System.out.println("Username: "+username);
       }
       filterChain.doFilter(request, response);
    }
}
