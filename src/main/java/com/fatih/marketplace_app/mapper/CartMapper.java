package com.fatih.marketplace_app.mapper;

import com.fatih.marketplace_app.dto.request.cart.CreateCartRequest;
import com.fatih.marketplace_app.dto.request.cart.UpdateCartRequest;
import com.fatih.marketplace_app.dto.response.cart.CartResponse;
import com.fatih.marketplace_app.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "user.id", source = "userId")
    CartEntity createCartRequestToEntity(CreateCartRequest createCartRequest);

    @Mapping(target = "cartId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "campaignId", source = "campaign.id")
    CartResponse toCartResponse(CartEntity cartEntity);

    List<CartResponse> toCartResponseList(List<CartEntity> cartEntities);

    @Mapping(target = "id", source = "cartId")
    CartEntity updateCartRequestToEntity(UpdateCartRequest updateCartRequest);

}
