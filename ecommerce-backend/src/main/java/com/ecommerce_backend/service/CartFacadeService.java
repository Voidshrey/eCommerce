package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.CartItemNotFoundException;
import com.ecommerce_backend.Exception.ProductNotFoundException;
import com.ecommerce_backend.Exception.UserNotFoundException;
import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.mapper.CartItemMapper;
import com.ecommerce_backend.mapper.CartMapper;
import com.ecommerce_backend.model.CartModel;
import com.ecommerce_backend.repository.CartItemRepository;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartFacadeService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    CartItemMapper cartItemMapper = CartItemMapper.INSTANCE;
    CartMapper cartMapper = CartMapper.INSTANCE;

    public CartFacadeService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartModel addProductsToCart(Long productId, int quantity, Long userId, String userNameFromToken) {

        if (ObjectUtils.isEmpty(productId) || ObjectUtils.isEmpty(quantity) || ObjectUtils.isEmpty(userId)) {
            throw new RuntimeException("productId, or userId or quantity is null ");
        }
        CartItem cartItem = new CartItem();
        Cart cartFromUserId = cartRepository.findByUserId(userId);
        Optional<Product> product = productRepository.findById(productId);
        Optional<User> user = userRepository.findById(userId);
        if (isUserNameEqualsToken(userNameFromToken, user)) {
            if (product.isPresent()) {
                if (Objects.isNull(cartFromUserId)) {
                    // if cart is not created for user -> create a cart with UserId
                    //get cartId // create cartItem with product and quantity and cartId
                    // save cartItem created // now update cart with cartItem
                    if (user.isPresent()) {
                        Cart cart = Cart.builder().user(user.get()).build();
                        Cart save = cartRepository.save(cart);
                        cartItem = CartItem.builder().product(product.get()).quantity(quantity).cart(save).build();
                        CartItem save1 = cartItemRepository.save(cartItem);
                        Cart cart1 = Cart.builder().id(save.getId()).user(user.get()).items(List.of(save1)).build();
                        return cartMapper.toDto(cartRepository.save(cart1));
                    } else {
                        throw new UserNotFoundException("userDetails nots found for userId : " + userId);
                    }
                }
                //cart already exists for user
                List<CartItem> cartItemsByCartId = cartRepository.findCartItemsByCartId(cartFromUserId.getId());
                Set<Long> productsSet = cartItemsByCartId.stream()
                        .map(items -> items.getProduct().getId()).collect(Collectors.toSet());
                if (productsSet.contains(productId)) {
                    // if already product exists increase the quantity
                    CartItem cartItemWithProduct = cartItemsByCartId.stream().filter(cartItem1 -> cartItem1.getProduct().getId().equals(productId))
                            .findFirst().get();
                    // added new quantity to cartItem with builder and updated it
                    cartItemRepository.save(CartItem.builder().id(cartItemWithProduct.getId())
                            .cart(cartItemWithProduct.getCart())
                            .product(cartItemWithProduct.getProduct()).quantity(cartItemWithProduct.getQuantity() + 1).build());
                    return cartMapper.toDto(cartRepository.findByUserId(userId));
                } else {
                    // if product is not in the cart
                    // create a cartItem with new product and quantity and save
                    // save this new cartItem to same cart
                    CartItem cartItemWithNewProduct = CartItem.builder().product(product.get())
                            .cart(cartFromUserId)
                            .quantity(quantity).build();
                    cartItemRepository.save(cartItemWithNewProduct);
                    return cartMapper.toDto(cartRepository.findByUserId(userId));
                }
            } else {
                throw new ProductNotFoundException(productId);
            }
        } else {
            throw new RuntimeException("UserId is not matching to make a change in cart");
        }
    }

    private static boolean isUserNameEqualsToken(String userNameFromToken, Optional<User> user) {
        return userNameFromToken.equals(user.get().getUsername());
    }

    public void updateCartItemQuantity(Long userId, int quantity, Long productId, String userNameFromToken) {
        Optional<CartItem> cartItem = cartItemRepository.findByCart_User_IdAndProduct_Id(userId, productId);
        Optional<User> user = userRepository.findById(userId);
        if (isUserNameEqualsToken(userNameFromToken, user)) {
            if (cartItem.isPresent()) {
                CartItem cartItem1 = cartItem.get();
                cartItem1.setQuantity(quantity);
                cartItemRepository.save(cartItem1);
            } else {
                throw new CartItemNotFoundException("Item not found to update");
            }
        } else {
            throw new RuntimeException("UserId is not matching to make a change in cart");
        }
    }

    public void deleteItem(Long userId, Long productId) {
        Optional<CartItem> byCartUserIdAndProductId =
                cartItemRepository.findByCart_User_IdAndProduct_Id(userId, productId);
        byCartUserIdAndProductId.ifPresent(cartItemRepository::delete);
    }
}
