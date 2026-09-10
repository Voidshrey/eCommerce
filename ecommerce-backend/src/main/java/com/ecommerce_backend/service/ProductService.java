package com.ecommerce_backend.service;

import com.ecommerce_backend.Exception.ProductNotFoundException;
import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.repository.ProductRepository;
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


    public List<Product> getAllProducts(String searchName){
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
               Product product2 = Product.builder().id(product1.getId()).name(product1.getName())
                       .price(product1.getPrice()).description(product.getDescription()).imageUrl(product1.getImageUrl())
                       .build();
                product2.setId(product1.getId());
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
}
