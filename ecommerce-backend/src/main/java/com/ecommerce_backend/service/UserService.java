package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.model.UserRegister;
import com.ecommerce_backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public @Nullable User findUserByEmail(String email) {
        if (!ObjectUtils.isEmpty(email)){
          return userRepository.findByEmail(email);
        }
        throw new UserNotFoundException(email);

    }

    public @Nullable User findUserByUserName(String userName) {
        if(ObjectUtils.isEmpty(userName)){
            throw new UserNotFoundException(userName);
        }
        return userRepository.findByUsername(userName);
    }

    public void registerUser(UserRegister userRegister) {
        if(!ObjectUtils.isEmpty(userRegister.getUserName()) && !ObjectUtils.isEmpty(userRegister.getEmail()) &&
                !ObjectUtils.isEmpty(userRegister.getPassword())){

            User userByEmail = userRepository.findByEmail(userRegister.getEmail());
            User userByName = userRepository.findByEmail(userRegister.getUserName());

            if(ObjectUtils.isEmpty(userByName) && ObjectUtils.isEmpty(userByEmail)){
                User saveUser = User.builder().email(userRegister.getEmail())
                        .username(userRegister.getUserName())
                        .passwordHash(bCryptPasswordEncoder.encode(userRegister.getPassword()))
                        .createdAt(LocalDateTime.now()).build();
                 userRepository.save(saveUser);
            }else{
                throw new RuntimeException("User is already registered please login");
            }

        }else{
            throw new RuntimeException("email or userName or password is empty or null");
        }
    }
}
