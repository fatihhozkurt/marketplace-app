package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.CartItemApi;
import com.fatih.marketplace_app.dto.request.cartItem.AddProductToCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.CreateCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.RemoveProductFromCartItemRequest;
import com.fatih.marketplace_app.dto.request.cartItem.UpdateCartItemRequest;
import com.fatih.marketplace_app.dto.response.cartItem.CartItemResponse;
import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.manager.service.CartItemService;
import com.fatih.marketplace_app.mapper.CartItemMapper;
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
public class CartItemController implements CartItemApi {

    private final CartItemService cartItemService;


    @Override
    public ResponseEntity<CartItemResponse> creatCartItem(CreateCartItemRequest createCartItemRequest) {

        CartItemEntity requestedCartItem = CartItemMapper.INSTANCE.createCartItemRequestToEntity(createCartItemRequest);
        CartItemEntity savedCartItem = cartItemService.createCartItem(requestedCartItem);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(savedCartItem);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CartItemResponse> getCartItemById(UUID cartItemId) {

        CartItemEntity foundCartItem = cartItemService.getCartItemById(cartItemId);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(foundCartItem);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<CartItemResponse> updateCartItem(UpdateCartItemRequest updateCartItemRequest) {

        CartItemEntity requestedCartItem = CartItemMapper.INSTANCE.updateCartItemRequestToEntity(updateCartItemRequest);
        CartItemEntity updatedCartItem = cartItemService.updateCartItem(requestedCartItem);
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getAllCartItems(Pageable pageable) {

        Page<CartItemEntity> cartItemEntities = cartItemService.getAllCartItems(pageable);
        List<CartItemResponse> cartItemResponses = CartItemMapper.INSTANCE.toCartItemResponseList(cartItemEntities.getContent());
        Map<UUID, List<CartItemResponse>> cartItemMap = cartItemResponses.stream().collect(Collectors.groupingBy(CartItemResponse::cartItemId));

        return new ResponseEntity<>(new PageImpl<>(List.of(cartItemMap), pageable, cartItemEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteCartItem(UUID cartItemId) {

        cartItemService.deleteCartItem(cartItemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CartItemResponse> addProductToCartItem(AddProductToCartItemRequest addProductToCartItemRequest) {

        CartItemEntity updatedCartItem = cartItemService.addProductToCartItem(addProductToCartItemRequest.cartItemId(), addProductToCartItemRequest.productId());
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartItemResponse> removeProductFromCartItem(RemoveProductFromCartItemRequest removeProductFromCartItemRequest) {

        CartItemEntity updatedCartItem = cartItemService.removeProductFromCartItem(removeProductFromCartItemRequest.cartItemId(), removeProductFromCartItemRequest.productId());
        CartItemResponse cartItemResponse = CartItemMapper.INSTANCE.toCartItemResponse(updatedCartItem);

        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<CartItemResponse>>>> getCartItemsByCartId(UUID cartId, Pageable pageable) {

        Page<CartItemEntity> cartItemEntities = cartItemService.getCartItemsByCartId(cartId, pageable);
        List<CartItemResponse> cartItemResponses = CartItemMapper.INSTANCE.toCartItemResponseList(cartItemEntities.getContent());
        Map<UUID, List<CartItemResponse>> cartItemMap = cartItemResponses.stream().collect(Collectors.groupingBy(CartItemResponse::cartItemId));

        return new ResponseEntity<>(new PageImpl<>(List.of(cartItemMap), pageable, cartItemEntities.getTotalElements()), HttpStatus.FOUND);
    }
}
