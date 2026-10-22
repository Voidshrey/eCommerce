package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.ProductNotFoundException;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.repository.ProductRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProductsBySearchName(String searchName){
        if(!ObjectUtils.isEmpty(searchName)){
            return productRepository.findByNameContainingIgnoreCase(searchName);
        }
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        if(!ObjectUtils.isEmpty(product.getId())){
            Optional<Product> byId = productRepository.findById(product.getId());
            if (byId.isPresent()){
               Product product1 = byId.get();
               Product product2 = Product.builder().id(product.getId()).name(product.getName())
                       .price(product.getPrice()).description(product.getDescription()).imageUrl(product.getImageUrl())
                       .build();
               return productRepository.save(product2);
            }else{
                return  productRepository.save(product);
            }
        }
        throw new ProductNotFoundException(product.getId());
    }

    public void deleteProduct(Product product) {
        if(!ObjectUtils.isEmpty(product.getId())){
            Optional<Product> byId = productRepository.findById(product.getId());
            if (byId.isPresent()){
                productRepository.delete(product);
            }else {
                throw new ProductNotFoundException(product.getId());
            }
        }
    }

    public @Nullable List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
