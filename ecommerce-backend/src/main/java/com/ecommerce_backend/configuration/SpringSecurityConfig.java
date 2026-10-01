package com.ecommerce_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        return httpSecurity
                //disbale login page by default spring secuirty
                .csrf(AbstractHttpConfigurer::disable)
                // allow some endpoints to access without token / username/pass
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry.requestMatchers("/api/user/register")
                                .permitAll().anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // default login for other endpoints
                .build();
    }

    // when we add srping security filter chain , spring starts seraching user details in db rather than in yml
    // so now I have said to spring to check in the inmemory by creating a mock of user , without checking db
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails userDetails = User
//                .builder()
//                .username("admin")
//                .password(bCryptPasswordEncoder().encode("admin123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }
}
