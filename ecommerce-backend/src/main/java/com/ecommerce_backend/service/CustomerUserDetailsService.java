package com.ecommerce_backend.service;

import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;

// this service is to bridge gap. telling the spring to find user details in db , when i try to login to authenticate
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!ObjectUtils.isEmpty(username)) {
            User byUsername = userRepository.findByUsername(username);
            User byEmail = userRepository.findByEmail(username);

            if (!ObjectUtils.isEmpty(byUsername)) {
                return org.springframework.security.core.userdetails.User
                        .builder()
                        .username(byUsername.getUsername())
                        .password(byUsername.getPasswordHash())
                        .roles("USER")
                        .build();

            } else if (!ObjectUtils.isEmpty(byEmail)) {
                return org.springframework.security.core.userdetails.User
                        .builder()
                        .username(byEmail.getUsername())
                        .password(byEmail.getPasswordHash())
                        .roles("USER")
                        .build();
            } else {
                throw new UsernameNotFoundException("userName or email Not found to authenticate pls verify : " + username);
            }

        } else {
            throw new RuntimeException("userName is empty please enter userName to login");
        }
    }
}
