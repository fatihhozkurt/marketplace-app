package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    ProductEntity createProduct(ProductEntity requestedProduct);

    Page<ProductEntity> getAllProducts(Pageable pageable);

    ProductEntity getProductById(UUID productId);

    ProductEntity updateProduct(ProductEntity requestedProduct);

    void deleteProduct(UUID productId);
}
