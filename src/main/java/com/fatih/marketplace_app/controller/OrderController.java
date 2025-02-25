package com.fatih.marketplace_app.controller;

import com.fatih.marketplace_app.controller.api.OrderApi;
import com.fatih.marketplace_app.dto.request.order.CreateOrderRequest;
import com.fatih.marketplace_app.dto.response.order.OrderResponse;
import com.fatih.marketplace_app.entity.OrderEntity;
import com.fatih.marketplace_app.manager.service.OrderService;
import com.fatih.marketplace_app.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 * REST controller for managing orders.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController implements OrderApi {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param createOrderRequest request containing order details.
     * @return ResponseEntity with the created order.
     */
    @Override
    public ResponseEntity<OrderResponse> createOrder(CreateOrderRequest createOrderRequest) {

        log.info("Creating a new order with details: {}", createOrderRequest);
        OrderEntity requestedOrder = OrderMapper.INSTANCE.createOrderRequestToEntity(createOrderRequest);
        OrderEntity createdOrder = orderService.createOrder(requestedOrder);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(createdOrder);
        log.info("Order created successfully with ID: {}", createdOrder.getId());

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId ID of the order to retrieve.
     * @return ResponseEntity with the order details.
     */
    @Override
    public ResponseEntity<OrderResponse> getOrderById(UUID orderId) {

        log.info("Fetching order with ID: {}", orderId);
        OrderEntity foundOrder = orderService.getOrderById(orderId);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(foundOrder);
        log.info("Order found: {}", orderResponse);

        return new ResponseEntity<>(orderResponse, HttpStatus.FOUND);
    }

    /**
     * Retrieves all orders with pagination.
     *
     * @param pageable pagination details.
     * @return ResponseEntity with a paginated list of orders.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getAllOrders(Pageable pageable) {

        log.info("Fetching all orders with pagination: {}", pageable);
        Page<OrderEntity> orderEntities = orderService.getAllOrders(pageable);
        List<OrderResponse> orderResponses = OrderMapper.INSTANCE.toOrderResponseList(orderEntities.getContent());
        Map<UUID, List<OrderResponse>> orderMap = orderResponses.stream().collect(Collectors.groupingBy(OrderResponse::orderId));
        log.info("Retrieved {} orders", orderResponses.size());

        return new ResponseEntity<>(new PageImpl<>(List.of(orderMap), pageable, orderEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Cancels an order by its ID.
     *
     * @param orderId ID of the order to cancel.
     * @return ResponseEntity with no content.
     */
    @Override
    public ResponseEntity<HttpStatus> cancelOrder(UUID orderId) {

        log.info("Cancelling order with ID: {}", orderId);
        orderService.cancelOrder(orderId);
        log.info("Order with ID: {} has been cancelled", orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves orders for a specific user with pagination.
     *
     * @param userId   ID of the user.
     * @param pageable pagination details.
     * @return ResponseEntity with a paginated list of orders.
     */
    @Override
    public ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getOrdersByUserId(UUID userId, Pageable pageable) {

        log.info("Fetching orders for user ID: {} with pagination: {}", userId, pageable);
        Page<OrderEntity> foundEntities = orderService.getOrdersByUserId(userId, pageable);
        List<OrderResponse> orderResponses = OrderMapper.INSTANCE.toOrderResponseList(foundEntities.getContent());
        Map<UUID, List<OrderResponse>> orderMap = orderResponses.stream().collect(Collectors.groupingBy(OrderResponse::orderId));
        log.info("Retrieved {} orders for user ID: {}", orderResponses.size(), userId);

        return new ResponseEntity<>(new PageImpl<>(List.of(orderMap), pageable, foundEntities.getTotalElements()), HttpStatus.OK);
    }

    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber unique order number.
     * @return ResponseEntity with the order details.
     */
    @Override
    public ResponseEntity<OrderResponse> getByOrderNumber(String orderNumber) {

        log.info("Fetching order with order number: {}", orderNumber);
        OrderEntity foundOrder = orderService.getOrderByOrderNumber(orderNumber);
        OrderResponse orderResponse = OrderMapper.INSTANCE.toOrderResponse(foundOrder);
        log.info("Order found: {}", orderResponse);

        return new ResponseEntity<>(orderResponse, HttpStatus.FOUND);
    }
}
