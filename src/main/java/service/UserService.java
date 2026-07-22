package com.saurabh.ecommerce.service;

import com.saurabh.ecommerce.entity.User;
import com.saurabh.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public long getUserCount(){

        return userRepository.count();

    }

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRole("ROLE_USER");

        return userRepository.save(user);

    }


    public User saveUser(User user){

        return userRepository.save(user);

    }


    public User getUserByEmail(String email){

        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException("User not found")
                );
    }

}