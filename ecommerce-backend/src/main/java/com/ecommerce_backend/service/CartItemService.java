package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.CartItemNotFoundException;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.mapper.CartItemMapper;
import com.ecommerce_backend.model.CartItemModel;
import com.ecommerce_backend.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    private final CartItemMapper cartItemMapper =  CartItemMapper.INSTANCE;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItemModel getCartItemById(Long cartItemId) {

        Optional<CartItem> byId = cartItemRepository.findById(cartItemId);
        if (!byId.isPresent()){
            throw new CartItemNotFoundException(cartItemId);
        }
        return cartItemMapper.toDto(byId.get());
    }

    public CartItem createCartItem(CartItem cartItem) {
        return  cartItemRepository.save(cartItem);
    }
}
