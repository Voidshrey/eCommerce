package com.ecommerce_backend.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel{

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
}
