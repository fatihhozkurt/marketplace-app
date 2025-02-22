package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDao {

    private final ProductRepository productRepository;

    public ProductEntity save(ProductEntity requestedProduct) {
        return productRepository.save(requestedProduct);
    }

    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductEntity> findById(UUID productId) {
        return productRepository.findById(productId);
    }

    public void delete(ProductEntity foundProduct) {
        productRepository.delete(foundProduct);
    }
}
