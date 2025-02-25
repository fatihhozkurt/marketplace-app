package com.fatih.marketplace_app.dao;

import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) for managing {@link ProductEntity} operations.
 */
@Component
@RequiredArgsConstructor
public class ProductDao {

    private final ProductRepository productRepository;

    /**
     * Saves the given product entity to the database.
     *
     * @param requestedProduct the product entity to save
     * @return the saved {@link ProductEntity}
     */
    public ProductEntity save(ProductEntity requestedProduct) {
        return productRepository.save(requestedProduct);
    }

    /**
     * Retrieves a paginated list of all products.
     *
     * @param pageable pagination information
     * @return a {@link Page} of {@link ProductEntity} objects
     */
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param productId the UUID of the product
     * @return an {@link Optional} containing the found product, or empty if not found
     */
    public Optional<ProductEntity> findById(UUID productId) {
        return productRepository.findById(productId);
    }

    /**
     * Deletes the given product entity from the database.
     *
     * @param foundProduct the product entity to delete
     */
    public void delete(ProductEntity foundProduct) {
        productRepository.delete(foundProduct);
    }
}
