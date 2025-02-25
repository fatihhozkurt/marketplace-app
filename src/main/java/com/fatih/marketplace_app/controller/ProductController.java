package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.ProductApi;
import com.fatih.marketplace_app.dto.request.product.CreateProductRequest;
import com.fatih.marketplace_app.dto.request.product.UpdateProductRequest;
import com.fatih.marketplace_app.dto.response.product.ProductResponse;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.manager.service.ProductService;
import com.fatih.marketplace_app.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller for handling product-related operations.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    /**
     * Creates a new product.
     *
     * @param createProductRequest The request containing product details.
     * @return The created product response.
     */
    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest createProductRequest) {

        log.info("Creating a new product: {}", createProductRequest);
        ProductEntity requestedProduct = ProductMapper.INSTANCE.createProductRequestToEntity(createProductRequest);
        ProductEntity savedProduct = productService.createProduct(requestedProduct);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(savedProduct);
        log.info("Product created successfully with ID: {}", productResponse.id());

        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable Pagination information.
     * @return A paginated list of products.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<ProductResponse>>>> getAllProducts(Pageable pageable) {

        log.info("Fetching all products with pagination: {}", pageable);
        Page<ProductEntity> productEntities = productService.getAllProducts(pageable);
        List<ProductResponse> productResponses = ProductMapper.INSTANCE.toProductResponseList(productEntities.getContent());
        Map<UUID, List<ProductResponse>> productMap = productResponses.stream()
                .collect(Collectors.groupingBy(ProductResponse::id));
        log.info("Fetched {} products", productResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(productMap), pageable, productEntities.getTotalElements()),
                HttpStatus.OK);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param productId The ID of the product.
     * @return The product response.
     */
    @Override
    public ResponseEntity<ProductResponse> getProductById(UUID productId) {

        log.info("Fetching product with ID: {}", productId);
        ProductEntity foundProduct = productService.getProductById(productId);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(foundProduct);
        log.info("Product found: {}", productResponse);

        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    /**
     * Updates an existing product.
     *
     * @param updateProductRequest The request containing updated product details.
     * @return The updated product response.
     */
    @Override
    public ResponseEntity<ProductResponse> updateProduct(UpdateProductRequest updateProductRequest) {

        log.info("Updating product: {}", updateProductRequest);
        ProductEntity requestedProduct = ProductMapper.INSTANCE.updateProductRequestToEntity(updateProductRequest);
        ProductEntity updatedProduct = productService.updateProduct(requestedProduct);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(updatedProduct);
        log.info("Product updated successfully: {}", productResponse);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId The ID of the product to delete.
     * @return HTTP status indicating success or failure.
     */
    @Override
    public ResponseEntity<HttpStatus> deleteProduct(UUID productId) {

        log.info("Deleting product with ID: {}", productId);
        productService.deleteProduct(productId);
        log.info("Product deleted successfully: {}", productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}