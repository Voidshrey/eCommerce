package com.ecommerce_backend.repository;

import com.ecommerce_backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

//    @Query("select c from CartItem c where c.product_id = :productId and c.cart_id.user_id = :userId")
//    Optional<CartItem> findByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);

    Optional<CartItem> findByCart_User_IdAndProduct_Id(@Param("userId") Long userId,@Param("productId") Long productId);
}