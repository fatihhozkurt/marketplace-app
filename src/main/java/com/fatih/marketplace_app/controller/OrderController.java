package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.OrderApi;
import com.fatih.marketplace_app.dto.request.order.CreateOrderRequest;
import com.fatih.marketplace_app.dto.response.order.OrderResponse;
import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.manager.service.OrderService;
import com.fatih.marketplace_app.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> createOrder(CreateOrderRequest createOrderRequest) {

        OrderEntity requestedOrder = OrderMapper.INSTANCE.createOrderRequestToEntity(createOrderRequest);
        OrderEntity createdOrder = orderService.createOrder(requestedOrder);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(createdOrder);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderResponse> getOrderById(UUID orderId) {

        OrderEntity foundOrder = orderService.getOrderById(orderId);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(foundOrder);

        return new ResponseEntity<>(orderResponse, HttpStatus.FOUND);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getAllOrders(Pageable pageable) {

        Page<OrderEntity> orderEntities = orderService.getAllOrders(pageable);
        List<OrderResponse> orderResponses = OrderMapper.INSTANCE.toOrderResponseList(orderEntities.getContent());
        Map<UUID, List<OrderResponse>> orderMap = orderResponses.stream().collect(Collectors.groupingBy(OrderResponse::orderId));

        return new ResponseEntity<>(new PageImpl<>(List.of(orderMap), pageable, orderEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> cancelOrder(UUID orderId) {

        orderService.cancelOrder(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getOrdersByUserId(UUID userId, Pageable pageable) {

        Page<OrderEntity> foundEntities = orderService.getOrdersByUserId(userId, pageable);
        List<OrderResponse> orderResponses = OrderMapper.INSTANCE.toOrderResponseList(foundEntities.getContent());
        Map<UUID, List<OrderResponse>> orderMap = orderResponses.stream().collect(Collectors.groupingBy(OrderResponse::orderId));

        return new ResponseEntity<>(new PageImpl<>(List.of(orderMap), pageable, foundEntities.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderResponse> getByOrderNumber(String orderNumber) {
        OrderEntity foundOrder = orderService.getOrderByOrderNumber(orderNumber);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(foundOrder);
        return new ResponseEntity<>(orderResponse, HttpStatus.FOUND);
    }
}
