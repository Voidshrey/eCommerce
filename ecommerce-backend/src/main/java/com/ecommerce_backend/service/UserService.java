package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        return userRepository.findByusername(userName);
    }
}
