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

/**
 * API interface for managing orders.
 */
@RequestMapping(ORDER)
public interface OrderApi {

    /**
     * Creates a new order.
     *
     * @param createOrderRequest The request body containing order details.
     * @return The created order response.
     */
    @PostMapping
    ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest);

    /**
     * Retrieves an order by its unique ID.
     *
     * @param orderId The unique identifier of the order.
     * @return The order response.
     */
    @GetMapping(ID)
    ResponseEntity<OrderResponse> getOrderById(@RequestParam("orderId") @NotNull UUID orderId);

    /**
     * Retrieves all orders with pagination.
     *
     * @param pageable Pagination details.
     * @return A paginated list of order responses grouped by order ID.
     */
    @GetMapping(ALL)
    ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getAllOrders(Pageable pageable);

    /**
     * Cancels an order by its unique ID.
     *
     * @param orderId The unique identifier of the order to be canceled.
     * @return HTTP status indicating the result of the cancellation.
     */
    @DeleteMapping
    ResponseEntity<HttpStatus> cancelOrder(@RequestParam("orderId") @NotNull UUID orderId);

    /**
     * Retrieves all orders associated with a specific user.
     *
     * @param userId   The unique identifier of the user.
     * @param pageable Pagination details.
     * @return A paginated list of order responses grouped by user ID.
     */
    @GetMapping(USER + ID)
    ResponseEntity<PageImpl<Map<UUID, List<OrderResponse>>>> getOrdersByUserId(@RequestParam("userId") @NotNull UUID userId, Pageable pageable);

    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber The 12-character order number.
     * @return The order response associated with the given order number.
     */
    @GetMapping(NUMBER)
    ResponseEntity<OrderResponse> getByOrderNumber(@RequestParam("orderNumber") @NotNull @Size(min = 12, max = 12) String orderNumber);
}
