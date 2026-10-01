package com.ecommerce_backend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartModel {
    private Long id;
    private Long userId;                // Reference to User by ID
    private List<CartItemModel> items;
}
