package com.riderecycle.repository;

import com.riderecycle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByEmail(String email);
   Optional<User> findByMobile(String mobile);
   Optional<User> findByUsername(String username);
}
