package com.ecommerce_backend.mapper;

import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.model.ProductModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product toEntity(ProductModel productModel);

    ProductModel toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductModel productModel, @MappingTarget Product product);
}