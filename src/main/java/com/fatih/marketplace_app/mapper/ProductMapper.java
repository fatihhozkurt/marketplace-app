package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.product.CreateProductRequest;
import com.fatih.marketplace_app.dto.request.product.UpdateProductRequest;
import com.fatih.marketplace_app.dto.response.product.ProductResponse;
import com.fatih.marketplace_app.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting product-related entities to DTOs and vice versa.
 */
@Mapper
public interface ProductMapper {

    /**
     * Singleton instance of the ProductMapper.
     */
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    /**
     * Converts a {@link ProductEntity} to a {@link ProductResponse}.
     *
     * @param productEntity The product entity to be converted.
     * @return The mapped {@link ProductResponse}.
     */
    ProductResponse toProductResponse(ProductEntity productEntity);

    /**
     * Converts a list of {@link ProductEntity} objects to a list of {@link ProductResponse} objects.
     *
     * @param content The list of product entities to be converted.
     * @return The mapped list of {@link ProductResponse} objects.
     */
    List<ProductResponse> toProductResponseList(List<ProductEntity> content);

    /**
     * Converts a {@link CreateProductRequest} DTO to a {@link ProductEntity}.
     *
     * @param createProductRequest The request object containing product details.
     * @return The mapped {@link ProductEntity}.
     */
    ProductEntity createProductRequestToEntity(CreateProductRequest createProductRequest);

    /**
     * Converts an {@link UpdateProductRequest} DTO to a {@link ProductEntity}.
     *
     * @param updateProductRequest The request object containing updated product details.
     * @return The mapped {@link ProductEntity}.
     */
    ProductEntity updateProductRequestToEntity(UpdateProductRequest updateProductRequest);
}