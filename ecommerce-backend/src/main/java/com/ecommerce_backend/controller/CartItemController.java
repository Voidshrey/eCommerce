package com.ecommerce_backend.controller;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.service.CartItemService;
import com.ecommerce_backend.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/cartItem")
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<CartItem> getAllCartItemById(@RequestParam Long cartItemId){
        return ResponseEntity.ok(cartItemService.getCartItemById(cartItemId));
    }

    @PostMapping
    public ResponseEntity<String> createCartItem(@RequestBody CartItem cartItem){
        return ResponseEntity.created(URI.create(cartItemService.createCartItem(cartItem).toString())).build();
    }

//    @GetMapping
//    public ResponseEntity<CartItem> getCartItemsByCartid(@RequestParam Long cartItemId){
//        return ResponseEntity.ok(cartItemService.getCartItemById(cartItemId));
//    }
}
