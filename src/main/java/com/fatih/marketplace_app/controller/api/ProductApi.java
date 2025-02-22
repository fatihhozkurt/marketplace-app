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

@RequestMapping(PRODUCT)
public interface ProductApi {

    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid CreateProductRequest createProductRequest);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<ProductResponse>>>> getAllProducts(Pageable pageable);

    @GetMapping(ID)
    ResponseEntity<ProductResponse> getProductById(@RequestParam("productId") @NotNull UUID productId);

    @PutMapping
    ResponseEntity<ProductResponse> updateProduct(@RequestBody @Valid UpdateProductRequest updateProductRequest);

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteProduct(@RequestParam("productId") @NotNull UUID productId);
}
