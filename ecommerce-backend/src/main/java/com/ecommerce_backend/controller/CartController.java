package com.ecommerce_backend.controller;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.service.CartService;
import com.ecommerce_backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/cart")
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItemsById(@RequestParam Long cartId){
        return ResponseEntity.ok(cartService.getAllItemsById(cartId));
    }

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody Cart cart){
        return ResponseEntity.created(URI.create(cartService.createCart(cart).toString())).build();
    }

//    findByUserId(Long userId)).

    @GetMapping("user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@RequestParam("userId") Long userId){
        return ResponseEntity.ok(cartService.findCartByUserId(userId));
    }

    @GetMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam("productid") Long productId,
                                                 @RequestParam("quantity") Long quantity,
                                                 @RequestParam("userId") Long userId){
        return ResponseEntity.ok(cartService.addProductsToCart(productId, quantity, userId));
    }

}
