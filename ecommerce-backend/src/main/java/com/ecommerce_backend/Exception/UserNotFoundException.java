package com.ecommerce_backend.Exception;

public class UserNotFoundException extends RuntimeException{

   public UserNotFoundException(String email){
        super("User not found for id : " +email);
    }
}
