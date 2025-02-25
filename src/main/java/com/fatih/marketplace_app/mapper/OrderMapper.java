package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.order.CreateOrderRequest;
import com.fatih.marketplace_app.dto.response.order.OrderResponse;
import com.fatih.marketplace_app.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting order-related entities to DTOs and vice versa.
 */
@Mapper(uses = {AddressMapper.class, CartItemMapper.class, UserMapper.class})
public interface OrderMapper {

    /**
     * Singleton instance of the OrderMapper.
     */
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * Converts a {@link CreateOrderRequest} DTO to an {@link OrderEntity}.
     *
     * @param createOrderRequest The request object containing order details.
     * @return The mapped {@link OrderEntity}.
     */
    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "wallet.id", source = "walletId")
    @Mapping(target = "address", source = "createAddressRequest")
    OrderEntity createOrderRequestToEntity(CreateOrderRequest createOrderRequest);


    /**
     * Converts an {@link OrderEntity} to an {@link OrderResponse}.
     *
     * @param orderEntity The order entity to be converted.
     * @return The mapped {@link OrderResponse}.
     */
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "firstName", source = "cart.user.firstName")
    @Mapping(target = "lastName", source = "cart.user.lastName")
    @Mapping(target = "cartItemResponses", source = "cart.cartItem")
    @Mapping(target = "addressResponse", source = "address")
    OrderResponse toOrderResponse(OrderEntity orderEntity);

    /**
     * Converts a list of {@link OrderEntity} objects to a list of {@link OrderResponse} objects.
     *
     * @param orderEntities The list of order entities to be converted.
     * @return The mapped list of {@link OrderResponse} objects.
     */
    List<OrderResponse> toOrderResponseList(List<OrderEntity> orderEntities);
}