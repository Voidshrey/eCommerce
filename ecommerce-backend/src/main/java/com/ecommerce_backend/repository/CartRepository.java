package com.ecommerce_backend.repository;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId")
    List<CartItem> findCartItemsByCartId(@Param("cartId") Long cartId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findByUserId(@Param("userId") Long userId);

//    "SELECT c FROM Cart c JOIN c.cartItems ci WHERE ci.id = :cartItemId"
    @Query("SELECT c FROM Cart c JOIN CartItem ci ON ci.cart.id = c.id where c.id =:cartItemCartId" )
    Cart findCartByCartItemCartId(@Param("cartItemCartId")Long cartItemCartId);


   // Optional<CartItem> findBycart_User_IdAndProduct_Id(@Param("userId") Long userId, @Param("productId") Long productId);
}