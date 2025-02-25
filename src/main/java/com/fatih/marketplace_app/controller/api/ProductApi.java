package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.product.CreateProductRequest;
import com.fatih.marketplace_app.dto.request.product.UpdateProductRequest;
import com.fatih.marketplace_app.dto.response.product.ProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

/**
 * API interface for managing products.
 */
@RequestMapping(PRODUCT)
public interface ProductApi {

    /**
     * Creates a new product.
     *
     * @param createProductRequest The request body containing product details.
     * @return The created product response.
     */
    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest);

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of product responses grouped by product ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<ProductResponse>>>> getAllProducts(Pageable pageable);

    /**
     * Retrieves a product by its unique ID.
     *
     * @param productId The unique identifier of the product.
     * @return The product response.
     */
    @GetMapping(ID)
    ResponseEntity<ProductResponse> getProductById(@RequestParam("productId") @NotNull UUID productId);

    /**
     * Updates an existing product.
     *
     * @param updateProductRequest The request body containing updated product details.
     * @return The updated product response.
     */
    @PutMapping
    ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid UpdateProductRequest updateProductRequest);

    /**
     * Deletes a product by its unique ID.
     *
     * @param productId The unique identifier of the product to be deleted.
     * @return HTTP status indicating the result of the deletion.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> deleteProduct(@RequestParam("productId") @NotNull UUID productId);
}