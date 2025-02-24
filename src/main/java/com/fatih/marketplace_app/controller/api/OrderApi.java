package com.fatih.marketplace_app.controller.api;

import com.fatih.marketplace_app.dto.request.order.CreateOrderRequest;
import com.fatih.marketplace_app.dto.response.order.OrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.UrlConst.*;

@RequestMapping(ORDER)
public interface OrderApi {

    @PostMapping
    ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest);

    @GetMapping(ID)
    ResponseEntity<OrderResponse> getOrderById(@RequestParam("orderId") @NotNull UUID orderId);

    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getAllOrders(Pageable pageable);

    @DeleteMapping
    ResponseEntity<HttpStatus> cancelOrder(@RequestParam("orderId") @NotNull UUID orderId);

    @GetMapping(USER + ID)
    ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getOrdersByUserId(@RequestParam("userId") @NotNull UUID userId, Pageable pageable);

    @GetMapping(NUMBER)
    ResponseEntity<OrderResponse> getByOrderNumber(@RequestParam("orderNumber") @NotNull @Size(min = 12, max = 12) String orderNumber);
}
