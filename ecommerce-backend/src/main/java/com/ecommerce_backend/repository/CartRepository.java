package com.ecommerce_backend.repository;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<CartItem> findCartItemsByCartId(@Param("cartId") Long cartId);

    @Nullable Cart findByUserId(Long userId);
}