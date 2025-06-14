package com.riderecycle.controller;

import com.riderecycle.payload.LoginDto;
import com.riderecycle.payload.UserDto;
import com.riderecycle.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //http://localhost:8083/api/v1/auth/registerUser

    // Postman JSON Body:
    /*
     {
        "username": "akdey074",
        "password": "test123",
        "email": "akdey074@gmail.com",
        "mobile": 9778042930
    }

    */
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult result
    ){
        // hashing the password before saving it to the database
        String hashpw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(12));
        userDto.setPassword(hashpw);
        if(result.hasErrors()) {
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        UserDto dto = userService.createUser(userDto);
        return new ResponseEntity<>("User Created!", HttpStatus.CREATED);
    }

    //http://localhost:8083/api/v1/auth/welcome

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to RideRecycle Application";
    }

    // Verify Login Credentials
    //http://localhost:8083/api/v1/auth/login

    // Postman JSON Body:

    /*

    {
    "username":"nkdey14",
    "password":"testing"
    }
     */
    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(
        @RequestBody LoginDto loginDto
    ) {
        String jwtToken = userService.verifyLoginCredentials(loginDto);
        if(jwtToken!=null){
            // Return token in JSON format
           // return ResponseEntity.ok(java.util.Collections.singletonMap("JWT token", jwtToken));
            return new ResponseEntity<>("JWT Token: "+jwtToken, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid Credentials!!", HttpStatus.UNAUTHORIZED);
        }
    }
}
