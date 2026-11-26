package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.ProductNotFoundException;
import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.mapper.CartMapper;
import com.ecommerce_backend.model.CartItemModel;
import com.ecommerce_backend.model.CartModel;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartFacadeService cartFacadeService;
    private final CartMapper cartMapper = CartMapper.INSTANCE;
    private final JwtUtils jwtUtils;

    public CartService(CartRepository cartRepository, CartFacadeService cartFacadeService, JwtUtils jwtUtils) {
        this.cartRepository = cartRepository;
        this.cartFacadeService = cartFacadeService;
        this.jwtUtils = jwtUtils;
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

    public  CartModel addProductsToCart(Long productId, int quantity, Long userId, HttpServletRequest httpServletRequest) {

        String userNameFromToken = getUserNameFromToken(httpServletRequest);

        if (ObjectUtils.isEmpty(productId) || ObjectUtils.isEmpty(quantity) || ObjectUtils.isEmpty(userId) || ObjectUtils.isEmpty(userNameFromToken)) {
            throw new RuntimeException("productId, or userId or quantity is null ");
        }

        return cartFacadeService.addProductsToCart(productId,quantity,userId, userNameFromToken);
    }

    private String getUserNameFromToken(HttpServletRequest httpServletRequest) {
        var token = httpServletRequest.getHeader(AUTHORIZATION).substring(7);

        String userNameFromToken = jwtUtils.getUserNameFromToken(token);
        return userNameFromToken;
    }

    public void updateCartItems(Long userId, int quantity, Long productId, HttpServletRequest httpServletRequest) {

        String userNameFromToken = getUserNameFromToken(httpServletRequest);

        if(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(quantity) && ObjectUtils.isEmpty(productId) && ObjectUtils.isEmpty(userNameFromToken)){
            throw new RuntimeException("Exception ocuured during update cart some of the fields are null or empty ");
        }
        cartFacadeService.updateCartItemQuantity(userId,quantity,productId, userNameFromToken);
    }

    public void deleteItemByProductId(Long userId, Long productId, HttpServletRequest httpServletRequest) {
        if(ObjectUtils.isEmpty(userId) && ObjectUtils.isEmpty(productId)){
            throw new ProductNotFoundException("ProductId not found ");
        }
        cartFacadeService.deleteItem(userId,productId);
    }
}
