package com.ecommerce_backend.controller;

import com.ecommerce_backend.entity.Product;
import com.ecommerce_backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/products")
@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProductsBySearchName(@RequestParam(required = false) String searchName){
        return ResponseEntity.ok(productService.getAllProductsBySearchName(searchName));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        return ResponseEntity.created(URI.create(productService.addProduct(product).getName())).build();
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.updateProduct(product,productId));
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestBody Product product){
        productService.deleteProduct(product);
        return ResponseEntity.accepted().build();
    }
}
