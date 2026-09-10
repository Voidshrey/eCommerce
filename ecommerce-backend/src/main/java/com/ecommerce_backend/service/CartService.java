package com.ecommerce_backend.service;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.repository.CartRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public @Nullable List<CartItem> getAllItemsById(Long cartId) {
        return cartRepository.findCartItemsByCartId(cartId);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public @Nullable Cart findCartByUserId(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {

        }

        return cartRepository.findByUserId(userId);
    }

    public @Nullable Cart addProductsToCart(Long productId, Long quantity, Long userId) {

        if (ObjectUtils.isEmpty(productId) || ObjectUtils.isEmpty(quantity) || ObjectUtils.isEmpty(userId)) {
            throw new RuntimeException("productId, or userId or quantity is null ");
        }

        return new Cart();
    }
}
