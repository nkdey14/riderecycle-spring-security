package com.riderecycle.controller;

import com.riderecycle.payload.UserDto;
import com.riderecycle.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

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
        // hashing the password before saving it to the database
        String hashpw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(12));
        userDto.setPassword(hashpw);
        UserDto dto = userService.createUser(userDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to RideRecycle Application";
    }
}
