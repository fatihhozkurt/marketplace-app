package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.ProductDao;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

/**
 * Service class responsible for managing product-related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductDao productDao;
    private final MessageSource messageSource;

    /**
     * Creates a new product and caches the result.
     *
     * @param requestedProduct the product entity to be created
     * @return the saved product entity
     */
    @CachePut(value = "products", key = "#result.id")
    @Transactional
    @Override
    public ProductEntity createProduct(ProductEntity requestedProduct) {
        log.info("Creating a new product: {}", requestedProduct);

        return productDao.save(requestedProduct);
    }

    /**
     * Retrieves all products with pagination support.
     *
     * @param pageable pagination information
     * @return a page of product entities
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        log.info("Fetching all products with pagination: {}", pageable);

        return productDao.findAll(pageable);
    }

    /**
     * Retrieves a product by its unique identifier, caching the result.
     *
     * @param productId the unique identifier of the product
     * @return the product entity
     * @throws ResourceNotFoundException if the product is not found
     */
    @Cacheable(value = "products", key = "#p0")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ProductEntity getProductById(UUID productId) {
        log.info("Fetching product by ID: {}", productId);

        return productDao.findById(productId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.PRD001",
                        new Object[]{productId},
                        Locale.getDefault())));
    }

    /**
     * Updates an existing product and evicts the cache entry.
     *
     * @param requestedProduct the product entity with updated information
     * @return the updated product entity
     */
    @CacheEvict(value = "products", key = "#p0.id")
    @Override
    public ProductEntity updateProduct(ProductEntity requestedProduct) {
        log.info("Updating product: {}", requestedProduct);

        ProductEntity foundProduct = getProductById(requestedProduct.getId());
        ProductEntity updatedProduct = checkUpdateConditions(requestedProduct, foundProduct);

        return productDao.save(updatedProduct);
    }

    /**
     * Deletes a product by its unique identifier.
     *
     * @param productId the unique identifier of the product to be deleted
     */
    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        log.info("Deleting product with ID: {}", productId);

        ProductEntity foundProduct = getProductById(productId);
        productDao.delete(foundProduct);
    }

    /**
     * Checks and updates product fields based on non-null values from the requested product.
     *
     * @param requestedProduct the product entity containing updated values
     * @param foundProduct     the existing product entity retrieved from the database
     * @return the updated product entity
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductEntity checkUpdateConditions(ProductEntity requestedProduct, ProductEntity foundProduct) {
        log.info("Checking update conditions for product: {}", requestedProduct.getId());

        if (requestedProduct.getProductName() != null) {
            foundProduct.setProductName(requestedProduct.getProductName());
        }
        if (requestedProduct.getProductDescription() != null) {
            foundProduct.setProductDescription(requestedProduct.getProductDescription());
        }
        if (requestedProduct.getProductPrice() != null) {
            foundProduct.setProductPrice(requestedProduct.getProductPrice());
        }
        if (requestedProduct.getStockQuantity() != null) {
            foundProduct.setStockQuantity(requestedProduct.getStockQuantity());
        }

        return foundProduct;
    }
}