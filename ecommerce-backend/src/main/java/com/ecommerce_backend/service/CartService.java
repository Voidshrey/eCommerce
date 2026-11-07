package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.mapper.CartMapper;
import com.ecommerce_backend.model.CartItemModel;
import com.ecommerce_backend.model.CartModel;
import com.ecommerce_backend.repository.CartRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartFacadeService cartFacadeService;
    private final CartMapper cartMapper = CartMapper.INSTANCE;

    public CartService(CartRepository cartRepository, CartFacadeService cartFacadeService) {
        this.cartRepository = cartRepository;
        this.cartFacadeService = cartFacadeService;
    }

    public @Nullable List<CartItemModel> getAllItemsById(Long cartId) {
        List<CartItem> cartItemsByCartId = cartRepository.findCartItemsByCartId(cartId);
        return cartMapper.toListDto(cartItemsByCartId);
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public CartModel findCartByUserId(Long userId) {
        if (ObjectUtils.isEmpty(userId)) {
            throw  new UserNotFoundException(userId.toString());
        }

        return cartMapper.toDto(cartRepository.findByUserId(userId));
    }

    public  CartModel addProductsToCart(Long productId, int quantity, Long userId) {

        if (ObjectUtils.isEmpty(productId) || ObjectUtils.isEmpty(quantity) || ObjectUtils.isEmpty(userId)) {
            throw new RuntimeException("productId, or userId or quantity is null ");
        }

        return cartFacadeService.addProductsToCart(productId,quantity,userId);
    }

    public void updateCartItems(Long userId, int quantity, Long productId) {

        if(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(quantity) && ObjectUtils.isEmpty(productId)){
            throw new RuntimeException("Exception ocuured during update cart some of the fields are null or empty ");
        }
        cartFacadeService.updateCartItemQuantity(userId,quantity,productId);
    }
}
