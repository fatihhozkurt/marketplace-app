package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.cartItem.CreateCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.UpdateCartItemRequest;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import com.fatih.marketplace_app.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ProductMapper.class})
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "cart.id", source = "cartId")
    CartItemEntity createCartItemRequestToEntity(CreateCartItemRequest createCartItemRequest);

    @Mapping(target = "id", source = "cartItemId")
    CartItemEntity updateCartItemRequestToEntity(UpdateCartItemRequest updateCartItemRequest);

    @Mapping(target = "productResponse", source = "product")
    @Mapping(target = "cartItemId", source = "id")
    CartItemResponse toCartItemResponse(CartItemEntity cartItemEntity);

    List<CartItemResponse> toCartItemResponseList(List<CartItemEntity> cartItemEntities);

}
