package com.ecommerce_backend.controller;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.model.CartItemModel;
import com.ecommerce_backend.model.CartModel;
import com.ecommerce_backend.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("")
    public ResponseEntity<List<CartItemModel>> getAllCartItemsById(@RequestParam Long cartId){
        return ResponseEntity.ok(cartService.getAllItemsById(cartId));
    }

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody Cart cart){
        return ResponseEntity.created(URI.create(cartService.createCart(cart).toString())).build();
    }

//    findByUserId(Long userId)).

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartModel> getCartByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(cartService.findCartByUserId(userId));
    }

    @PostMapping("/add/user/{userId}/product/{productId}")
    public ResponseEntity<CartModel> addProductToCart(@PathVariable("productId") Long productId,
                                                      @RequestParam("quantity") int quantity,
                                                      @PathVariable("userId") Long userId, HttpServletRequest httpServletRequest){
        return ResponseEntity.ok(cartService.addProductsToCart(productId, quantity, userId,httpServletRequest));
    }

    @PutMapping("/add/cart/user/{userId}/product/{productId}")
    public ResponseEntity<?> updateCartQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity, HttpServletRequest httpServletRequest) {
        cartService.updateCartItems(userId, quantity,productId, httpServletRequest);
        return ResponseEntity.ok("Quantity updated cleanly!");
    }

    @DeleteMapping("/delete/user/{userId}/product/{productId}")
    public ResponseEntity<String> deleteItemByProductId(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId, HttpServletRequest httpServletRequest) {
        cartService.deleteItemByProductId(userId, productId, httpServletRequest);
        return ResponseEntity.ok("Deleted Successfully!!!");
    }
}
