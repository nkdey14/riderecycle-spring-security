package com.riderecycle.service;

import com.riderecycle.payload.LoginDto;
import com.riderecycle.payload.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    boolean verifyLoginCredentials(LoginDto loginDto);
}
