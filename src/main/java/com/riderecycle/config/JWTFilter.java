package com.riderecycle.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.riderecycle.entity.User;
import com.riderecycle.repository.UserRepository;
import com.riderecycle.service.impl.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter{

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
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
           Optional<User> opUsername =
                   userRepository.findByUsername(username);

              if(opUsername.isPresent()) {
                  User user = opUsername.get();
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
                  authenticationToken.setDetails(new WebAuthenticationDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
              }

       }
       filterChain.doFilter(request, response);
    }
}
