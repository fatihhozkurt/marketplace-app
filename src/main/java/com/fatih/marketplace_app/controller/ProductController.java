package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.ProductApi;
import com.fatih.marketplace_app.dto.request.product.CreateProductRequest;
import com.fatih.marketplace_app.dto.request.product.UpdateProductRequest;
import com.fatih.marketplace_app.dto.response.product.ProductResponse;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.manager.service.ProductService;
import com.fatih.marketplace_app.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;


    @Override
    public ResponseEntity<ProductResponse> createProduct(CreateProductRequest createProductRequest) {

        ProductEntity requestedProduct = ProductMapper.INSTANCE.createProductRequestToEntity(createProductRequest);
        ProductEntity savedProduct = productService.createProduct(requestedProduct);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(savedProduct);

        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<ProductResponse>>>> getAllProducts(Pageable pageable) {

        Page<ProductEntity> productEntities = productService.getAllProducts(pageable);
        List<ProductResponse> productResponses = ProductMapper.INSTANCE.toProductResponseList(productEntities.getContent());
        Map<UUID, List<ProductResponse>> productMap = productResponses.stream()
                .collect(Collectors.groupingBy(ProductResponse::id));

        return new ResponseEntity<>(new PageImpl<>(List.of(productMap), pageable, productEntities.getTotalElements()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(UUID productId) {

        ProductEntity foundProduct = productService.getProductById(productId);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(foundProduct);

        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(UpdateProductRequest updateProductRequest) {

        ProductEntity requestedProduct = ProductMapper.INSTANCE.updateProductRequestToEntity(updateProductRequest);
        ProductEntity updatedProduct = productService.updateProduct(requestedProduct);
        ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(updatedProduct);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteProduct(UUID productId) {

        productService.deleteProduct(productId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}