package com.ecommerce_backend.Exception;

public class CartItemNotFoundException extends RuntimeException{

   public CartItemNotFoundException(Long id){
        super("Cart Item not found for id :" +id);
    }
}
