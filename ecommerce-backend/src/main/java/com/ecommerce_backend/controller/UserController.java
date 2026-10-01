package com.ecommerce_backend.controller;

import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.model.UserRegister;
import com.ecommerce_backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/api/user")
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    //findByUsername(String username)

    @GetMapping("/userName")
    public ResponseEntity<User> getUserByUsername(@RequestParam String userName){
        return ResponseEntity.ok(userService.findUserByUserName(userName));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegister userRegister) {
        try {
            userService.registerUser(userRegister);
            return ResponseEntity.created(URI.create("created")).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
