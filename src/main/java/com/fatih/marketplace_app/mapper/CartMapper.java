package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
import com.fatih.marketplace_app.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting cart-related DTOs to entities and vice versa.
 */
@Mapper
public interface CartMapper {

    /**
     * Singleton instance of the CartMapper.
     */
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    /**
     * Converts a {@link CreateCartRequest} to a {@link CartEntity}.
     *
     * @param createCartRequest The request DTO containing cart creation details.
     * @return The mapped {@link CartEntity}.
     */
    @Mapping(target = "user.id", source = "userId")
    CartEntity createCartRequestToEntity(CreateCartRequest createCartRequest);

    /**
     * Converts a {@link CartEntity} to a {@link CartResponse}.
     *
     * @param cartEntity The cart entity to be converted.
     * @return The mapped {@link CartResponse}.
     */
    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "campaignId", source = "campaign.id")
    CartResponse toCartResponse(CartEntity cartEntity);

    /**
     * Converts a list of {@link CartEntity} objects to a list of {@link CartResponse} objects.
     *
     * @param cartEntities The list of cart entities to be converted.
     * @return The mapped list of {@link CartResponse} objects.
     */
    List<CartResponse> toCartResponseList(List<CartEntity> cartEntities);

    /**
     * Converts an {@link UpdateCartRequest} to a {@link CartEntity}.
     *
     * @param updateCartRequest The request DTO containing cart update details.
     * @return The mapped {@link CartEntity}.
     */
    @Mapping(target = "id", source = "cartId")
    CartEntity updateCartRequestToEntity(UpdateCartRequest updateCartRequest);
}