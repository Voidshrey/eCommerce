package com.ecommerce_backend.controller;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.service.CartService;
import com.ecommerce_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/user")
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    //findByUsername(String username)

    @GetMapping
    public ResponseEntity<User> getUserByUsername(@RequestParam String userName){
        return ResponseEntity.ok(userService.findUserByUserName(userName));
    }

//    @PostMapping
//    public ResponseEntity<String> createCart(@RequestBody Cart cart){
//        return ResponseEntity.created(URI.create(cartService.createCart(cart).toString())).build();
//    }

}
