package com.fatih.marketplace_app.manager.service;

import com.fatih.marketplace_app.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing product operations.
 */
public interface ProductService {

    /**
     * Creates a new product.
     *
     * @param requestedProduct The product entity to be created.
     * @return The created product entity.
     */
    ProductEntity createProduct(ProductEntity requestedProduct);

    /**
     * Retrieves all products with pagination support.
     *
     * @param pageable The pagination information.
     * @return A paginated list of product entities.
     */
    Page<ProductEntity> getAllProducts(Pageable pageable);

    /**
     * Retrieves a product by its unique ID.
     *
     * @param productId The unique identifier of the product.
     * @return The product entity if found.
     */
    ProductEntity getProductById(UUID productId);

    /**
     * Updates an existing product.
     *
     * @param requestedProduct The updated product entity.
     * @return The updated product entity.
     */
    ProductEntity updateProduct(ProductEntity requestedProduct);

    /**
     * Deletes a product by its unique ID.
     *
     * @param productId The unique identifier of the product to be deleted.
     */
    void deleteProduct(UUID productId);
}
