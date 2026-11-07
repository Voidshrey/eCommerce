package com.ecommerce_backend.mapper;

import com.ecommerce_backend.entity.Cart;
import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.model.CartItemModel;
import com.ecommerce_backend.model.CartModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
    Cart toEntity(CartModel cartModel);

    @AfterMapping
    default void linkItems(@MappingTarget Cart cart) {
        cart.getItems().forEach(item -> item.setCart(cart));
    }

    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "product.id", target = "productId")
//    @Mapping(source = "product.name" , target = "productName")
    CartModel toDto(Cart cart);

    List<CartModel> toDtoList(List<Cart> cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemModel toDto(CartItem item);

//    @Mapping(source = "product.id", target = "productId")
//    @Mapping(source = "product.name", target = "productName")
//    @Mapping(source = "product.price", target = "price")
    List<CartItemModel> toListDto(List<CartItem> item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cart partialUpdate(CartModel cartModel, @MappingTarget Cart cart);
}