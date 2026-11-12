package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.model.LoginRequest;
import com.ecommerce_backend.model.LoginResponse;
import com.ecommerce_backend.model.UserRegister;
import com.ecommerce_backend.repository.UserRepository;
import com.ecommerce_backend.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }


    public void registerUser(UserRegister userRegister) {
        if (!ObjectUtils.isEmpty(userRegister.getUserName()) && !ObjectUtils.isEmpty(userRegister.getEmail()) &&
                !ObjectUtils.isEmpty(userRegister.getPassword())) {

            User userByEmail = userRepository.findByEmail(userRegister.getEmail());
            User userByName = userRepository.findByUsername(userRegister.getUserName());

            if (ObjectUtils.isEmpty(userByName) && ObjectUtils.isEmpty(userByEmail)) {
                User saveUser = User.builder().email(userRegister.getEmail())
                        .username(userRegister.getUserName())
                        .passwordHash(bCryptPasswordEncoder.encode(userRegister.getPassword()))
                        .role("ROLE_USER")
                        .createdAt(LocalDateTime.now()).build();
                userRepository.save(saveUser);
            } else {
                throw new RuntimeException("User is already registered please login");
            }

        } else {
            throw new RuntimeException("email or userName or password is empty or null");
        }
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {

        if (!ObjectUtils.isEmpty(loginRequest) &&
                !ObjectUtils.isEmpty(loginRequest.getPassword())
                &&
                !ObjectUtils.isEmpty(loginRequest.getUsername())) {
            var registerUser = userRepository.findByUsername(loginRequest.getUsername());
            if (!ObjectUtils.isEmpty(registerUser)) {
                if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), registerUser.getPasswordHash())) {
                    var token = jwtUtils.generateToken(loginRequest.getUsername(), registerUser.getRole());
                    return LoginResponse.builder().
                            token(token)
                            .role(jwtUtils.getRoleFromToken(token))
                            .responseDesc("SUCCESS")
                            .build();
                } else {
                    throw new UserNotFoundException("Password is not matching check");
                }
            } else {
                throw new UserNotFoundException("userName not found please register before login");
            }
        }
        throw new RuntimeException("Username or password is not present");
    }
}
