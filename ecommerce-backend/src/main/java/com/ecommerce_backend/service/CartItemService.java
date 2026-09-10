package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.CartItemNotFoundException;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.repository.CartItemRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public @Nullable CartItem getCartItemById(Long cartItemId) {

        Optional<CartItem> byId = cartItemRepository.findById(cartItemId);
        if (!byId.isPresent()){
            throw new CartItemNotFoundException(cartItemId);
        }
        return byId.get();
    }

    public CartItem createCartItem(CartItem cartItem) {
        return  cartItemRepository.save(cartItem);
    }
}
