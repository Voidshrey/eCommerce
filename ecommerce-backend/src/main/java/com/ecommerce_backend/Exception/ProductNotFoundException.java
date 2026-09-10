package com.ecommerce_backend.Exception;

public class ProductNotFoundException extends RuntimeException{

   public ProductNotFoundException(Long id){
        super("Product details not found for id :" +id);
    }
}
