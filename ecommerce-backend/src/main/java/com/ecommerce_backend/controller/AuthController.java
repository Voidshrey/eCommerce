package com.ecommerce_backend.controller;

import com.ecommerce_backend.model.LoginRequest;
import com.ecommerce_backend.model.LoginResponse;
import com.ecommerce_backend.model.UserRegister;
import com.ecommerce_backend.service.AuthService;
import com.ecommerce_backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.RequestInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginRequest(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.loginUser(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(LoginResponse.builder().responseDesc(e.getMessage()).token("").build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
        try {
            authService.registerUser(userRegister);
            return ResponseEntity.created(URI.create("created")).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest) {
        try {
            authService.logOutUser(httpServletRequest);
            return ResponseEntity.ok("logout was successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
