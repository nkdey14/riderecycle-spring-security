package com.riderecycle.service.impl;

import com.riderecycle.entity.User;
import com.riderecycle.payload.LoginDto;
import com.riderecycle.payload.UserDto;
import com.riderecycle.repository.UserRepository;
import com.riderecycle.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    User mapToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    UserDto mapToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        Optional<User> byUsername = userRepository.findByUsername(userDto.getUsername());
        if(byUsername.isPresent()) {
            throw new RuntimeException("Username already exists!! Please try with a different username");
        }

        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        if(byEmail.isPresent()) {
            throw new RuntimeException("Email ID already exists!! Please try with a different Email ID");
        }

        Optional<User> byMobile = userRepository.findByMobile(userDto.getMobile());
        if(byMobile.isPresent()) {
            throw new RuntimeException("Mobile No. already exists!! Please try with a different Mobile No.");
        }

        User user = mapToUserEntity(userDto);
        User savedUser = userRepository.save(user);
        return mapToUserDto(savedUser);
    }

    @Override
    public String verifyLoginCredentials(LoginDto loginDto) {
        Optional<User> opUsername = userRepository.findByUsername(loginDto.getUsername());
        if (opUsername.isPresent()) {
            User user = opUsername.get();
            boolean checkpw = BCrypt.checkpw(loginDto.getPassword(), user.getPassword());
            if(checkpw) {
               return jwtService.generateToken(user.getUsername());
            }
        }
        return null;
    }
}
