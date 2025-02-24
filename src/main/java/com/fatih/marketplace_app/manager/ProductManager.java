package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.ProductDao;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
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


@Service
@RequiredArgsConstructor
public class ProductManager implements ProductService {

    private final ProductDao productDao;
    private final MessageSource messageSource;

    @CachePut(value = "products", key = "#result.id")
    @Transactional
    @Override
    public ProductEntity createProduct(ProductEntity requestedProduct) {
        return productDao.save(requestedProduct);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<ProductEntity> getAllProducts(Pageable pageable) {

        return productDao.findAll(pageable);
    }

    @Cacheable(value = "products", key = "#p0")
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ProductEntity getProductById(UUID productId) {
        return productDao.findById(productId).orElseThrow(() -> new ResourceNotFoundException(messageSource
                .getMessage("backend.exceptions.PRD001",
                        new Object[]{productId},
                        Locale.getDefault())));
    }

    @CacheEvict(value = "products", key = "#p0.id")
    @Override
    public ProductEntity updateProduct(ProductEntity requestedProduct) {

        ProductEntity foundProduct = getProductById(requestedProduct.getId());
        ProductEntity updatedProduct = checkUpdateConditions(requestedProduct, foundProduct);

        return productDao.save(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId) {
        ProductEntity foundProduct = getProductById(productId);
        productDao.delete(foundProduct);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ProductEntity checkUpdateConditions(ProductEntity requestedProduct, ProductEntity foundProduct) {

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