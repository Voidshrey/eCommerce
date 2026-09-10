package com.ecommerce_backend.service;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.entity.User;
import com.ecommerce_backend.repository.CartRepository;
import com.ecommerce_backend.repository.ProductRepository;
import com.ecommerce_backend.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CartFacadeService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartFacadeService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public @Nullable Cart addProductsToCart(Long productId, int quantity, Long userId) {

        if(ObjectUtils.isEmpty(productId)|| ObjectUtils.isEmpty(quantity)|| ObjectUtils.isEmpty(userId)){
            throw new RuntimeException("productId, or userId or quantity is null ");
        }
        Cart cartRepositoryByUserId = cartRepository.findByUserId(userId);
        Optional<Product> product = productRepository.findById(productId);
        CartItem cartItem = CartItem.builder().product(product.get()).quantity(quantity).build();
        if(ObjectUtils.isEmpty(cartRepositoryByUserId)){

            User user = userRepository.findbyUserId(userId);
            Cart cart = Cart.builder().user(user).items(cartItem.getCart().getItems()).build();
            return  cartRepository.save(cart);
            // create cart
        }
        //cart already exists for user
        // if already product exists increase the quantity by 1
        List<CartItem> cartItemsByCartId = cartRepository.findCartItemsByCartId(cartRepositoryByUserId.getId());
        cartItemsByCartId.stream().filter(cartItem1 -> cartItem1.getProduct().getId().equals(productId))
                .forEach(cartItem1 -> cartItem1.setQuantity(cartItem1.getQuantity() + 1 ));
        // add products to cart with quantity
        List<CartItem> items = cartRepositoryByUserId.getItems();
        items.add(cartItem);
        cartRepositoryByUserId.setItems(items);

        return cartRepository.save(cartRepositoryByUserId);
    }
}
