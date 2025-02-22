package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.product.CreateProductRequest;
import com.fatih.marketplace_app.dto.request.product.UpdateProductRequest;
import com.fatih.marketplace_app.dto.response.product.ProductResponse;
import com.fatih.marketplace_app.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponse toProductResponse(ProductEntity productEntity);

    List<ProductResponse> toProductResponseList(List<ProductEntity> content);

    ProductEntity createProductRequestToEntity(CreateProductRequest createProductRequest);

    ProductEntity updateProductRequestToEntity(UpdateProductRequest updateProductRequest);
}
