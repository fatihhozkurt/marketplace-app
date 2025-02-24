package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.order.CreateOrderRequest;
import com.fatih.marketplace_app.dto.response.order.OrderResponse;
import com.fatih.marketplace_app.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AddressMapper.class, CartItemMapper.class, UserMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "cart.id", source = "cartId")
    @Mapping(target = "wallet.id", source = "walletId")
    @Mapping(target = "address", source = "createAddressRequest")
    OrderEntity createOrderRequestToEntity(CreateOrderRequest createOrderRequest);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "firstName", source = "cart.user.firstName")
    @Mapping(target = "lastName", source = "cart.user.lastName")
    @Mapping(target = "cartItemResponses", source = "cart.cartItem")
    @Mapping(target = "addressResponse", source = "address")
    OrderResponse toOrderResponse(OrderEntity orderEntity);

    List<OrderResponse> toOrderResponseList(List<OrderEntity> orderEntities);

}
