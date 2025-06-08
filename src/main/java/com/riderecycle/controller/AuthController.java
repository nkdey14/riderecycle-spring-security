package com.riderecycle.controller;

import com.riderecycle.payload.UserDto;
import com.riderecycle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8083/api/v1/auth/registerUser
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(
        @RequestBody UserDto userDto
    ){
        UserDto dto = userService.createUser(userDto);
        // Logic for user registration will go here
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
