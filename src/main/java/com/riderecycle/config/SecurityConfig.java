package com.riderecycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import com.riderecycle.repository.UserRepository;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;
    private UserRepository userRepository;

    public SecurityConfig(JWTFilter jwtFilter, UserRepository userRepository) {
        this.jwtFilter = jwtFilter;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .addFilterBefore(jwtFilter, AuthorizationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/login", "/api/v1/auth/user/signUp").permitAll()
                        .requestMatchers("/api/v1/auth/welcome").hasRole("" +
                                "USER")
                        .anyRequest().authenticated());

        return http.build();
    }
}
