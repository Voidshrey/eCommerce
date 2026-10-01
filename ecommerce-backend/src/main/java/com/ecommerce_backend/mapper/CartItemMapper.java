package com.ecommerce_backend.mapper;

import com.ecommerce_backend.entity.CartItem;
import com.ecommerce_backend.model.CartItemModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItem toEntity(CartItemModel cartItemModel);

    CartItemModel toDto(CartItem cartItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(CartItemModel cartItemModel, @MappingTarget CartItem cartItem);
}