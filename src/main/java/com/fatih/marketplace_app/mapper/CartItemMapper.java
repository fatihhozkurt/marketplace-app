package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.cartItem.CreateCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.UpdateCartItemRequest;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import com.fatih.marketplace_app.entity.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting cart item-related DTOs to entities and vice versa.
 */
@Mapper(uses = {ProductMapper.class})
public interface CartItemMapper {

    /**
     * Singleton instance of the CartItemMapper.
     */
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    /**
     * Converts a {@link CreateCartItemRequest} to a {@link CartItemEntity}.
     *
     * @param createCartItemRequest The request DTO containing cart item creation details.
     * @return The mapped {@link CartItemEntity}.
     */
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "cart.id", source = "cartId")
    CartItemEntity createCartItemRequestToEntity(CreateCartItemRequest createCartItemRequest);

    /**
     * Converts an {@link UpdateCartItemRequest} to a {@link CartItemEntity}.
     *
     * @param updateCartItemRequest The request DTO containing cart item update details.
     * @return The mapped {@link CartItemEntity}.
     */
    @Mapping(target = "id", source = "cartItemId")
    CartItemEntity updateCartItemRequestToEntity(UpdateCartItemRequest updateCartItemRequest);

    /**
     * Converts a {@link CartItemEntity} to a {@link CartItemResponse}.
     *
     * @param cartItemEntity The cart item entity to be converted.
     * @return The mapped {@link CartItemResponse}.
     */
    @Mapping(target = "productResponse", source = "product")
    @Mapping(target = "cartItemId", source = "id")
    CartItemResponse toCartItemResponse(CartItemEntity cartItemEntity);

    /**
     * Converts a list of {@link CartItemEntity} objects to a list of {@link CartItemResponse} objects.
     *
     * @param cartItemEntities The list of cart item entities to be converted.
     * @return The mapped list of {@link CartItemResponse} objects.
     */
    List<CartItemResponse> toCartItemResponseList(List<CartItemEntity> cartItemEntities);
}