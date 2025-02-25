package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.OrderDao;
import com.fatih.marketplace_app.entity.*;
import com.fatih.marketplace_app.exception.BusinessException;
import com.fatih.marketplace_app.manager.service.AddressService;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.OrderService;
import com.fatih.marketplace_app.manager.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Manager class responsible for order operations.
 * Provides functionality for creating, retrieving, and canceling orders.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService {

    private final OrderDao orderDao;
    private final CartService cartService;
    private final WalletService walletService;
    private final AddressService addressService;
    private final MessageSource messageSource;

    /**
     * Creates a new order with validation of wallet balance and product stock.
     *
     * @param requestedOrder The order entity to be created
     * @return The created order entity
     * @throws BusinessException if wallet balance is insufficient or product stock is unavailable
     */
    @Transactional
    @Override
    public OrderEntity createOrder(OrderEntity requestedOrder) {
        log.info("Creating new order for cart ID: {}", requestedOrder.getCart().getId());

        CartEntity foundCart = cartService.getCartById(requestedOrder.getCart().getId());
        log.debug("Retrieved cart with ID: {}", foundCart.getId());

        WalletEntity foundWallet = walletService.getWalletById(requestedOrder.getWallet().getId());
        log.debug("Retrieved wallet with ID: {}", foundWallet.getId());

        AddressEntity createdAddress = addressService.createAddress(requestedOrder.getAddress());
        log.debug("Created address with ID: {}", createdAddress.getId());


        requestedOrder.setUser(foundCart.getUser());
        requestedOrder.setAddress(createdAddress);

        log.debug("Validating wallet balance. Current balance: {}, Required amount: {}",
                foundWallet.getBalance(), foundCart.getCartPrice());
        validateWalletBalance(foundWallet, foundCart.getCartPrice());

        log.debug("Validating and deducting product stock");
        validateAndDeductProductStock(foundCart);

        log.debug("Updating wallet balance");
        updateWalletBalance(foundWallet, foundCart.getCartPrice());

        requestedOrder.setOrderNumber(generateRandomOrderNumber());
        log.debug("Generated order number");

        requestedOrder.setFinalPrice(foundCart.getCartPrice());
        log.debug("Set final price to CartEntity");

        return orderDao.save(requestedOrder);
    }

    /**
     * Validates that the wallet has sufficient balance for the order.
     *
     * @param wallet The wallet entity to validate
     * @param cartPrice The total price of the cart
     * @throws BusinessException if wallet balance is insufficient
     */
    private void validateWalletBalance(WalletEntity wallet, BigDecimal cartPrice) {
        log.debug("Validating wallet balance: {} against cart price: {}", wallet.getBalance(), cartPrice);

        if (wallet.getBalance().compareTo(cartPrice) < 0) {
            log.warn("Insufficient wallet balance. Wallet ID: {}, Balance: {}, Required: {}",
                    wallet.getId(), wallet.getBalance(), cartPrice);

            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT003",
                            new Object[]{wallet.getId()},
                            Locale.getDefault())
            );
        }
    }

    /**
     * Validates that all products in the cart have sufficient stock
     * and deducts the quantities from stock.
     *
     * @param cart The cart entity containing the items to validate
     * @throws BusinessException if any product has insufficient stock
     */
    private void validateAndDeductProductStock(CartEntity cart) {
        log.debug("Validating product stock for {} cart items", cart.getCartItem().size());

        cart.getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();

            log.trace("Checking stock for product ID: {}. Available: {}, Required: {}",
                    product.getId(), product.getStockQuantity(), item.getProductQuantity());

            if (product.getStockQuantity() < item.getProductQuantity()) {
                log.warn("Insufficient product stock. Product ID: {}, Available: {}, Required: {}",
                        product.getId(), product.getStockQuantity(), item.getProductQuantity());

                throw new BusinessException(
                        messageSource.getMessage("backend.exceptions.PRD002", new Object[]{product.getId()},
                                Locale.getDefault())
                );
            }
        });

        log.debug("Deducting quantities from product stock");
        cart.getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getProductQuantity());
            log.trace("Updated stock");
            log.debug("Product stock validation and deduction completed successfully");
        });
    }

    /**
     * Updates the wallet balance by subtracting the cart price.
     *
     * @param wallet The wallet entity to update
     * @param cartPrice The amount to subtract from the wallet balance
     */
    private void updateWalletBalance(WalletEntity wallet, BigDecimal cartPrice) {
        log.debug("Updating wallet balance");
        wallet.setBalance(wallet.getBalance().subtract(cartPrice));
        log.debug("Wallet balance updated successfully");
    }

    /**
     * Generates a random 12-digit order number.
     *
     * @return A random 12-digit order number as a String
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String generateRandomOrderNumber() {
        log.debug("Generating random order number");

        return new Random().ints(12, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * Retrieves an order by its order number.
     *
     * @param orderNumber The order number to search for
     * @return The found order entity
     * @throws BusinessException if no order exists with the given order number
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public OrderEntity getOrderByOrderNumber(String orderNumber) {
        log.info("Retrieving order with order number: {}", orderNumber);

        return orderDao.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(
                        messageSource.getMessage("backend.exceptions.ORD001",
                                new Object[]{orderNumber}, Locale.getDefault())));
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve
     * @return The found order entity
     * @throws BusinessException if no order exists with the given ID
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public OrderEntity getOrderById(UUID orderId) {
        log.info("Retrieving order with ID: {}", orderId);

        return orderDao.findById(orderId)
                .orElseThrow(() -> new BusinessException(
                        messageSource.getMessage("backend.exceptions.ORD001",
                                new Object[]{orderId}, Locale.getDefault())));
    }

    /**
     * Retrieves all orders with pagination.
     *
     * @param pageable Pagination information
     * @return A page of order entities
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<OrderEntity> getAllOrders(Pageable pageable) {
        log.info("Retrieving all orders with pagination. Page: {}, Size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return orderDao.findAll(pageable);
    }

    /**
     * Cancels an order, restoring wallet balance and product stock.
     *
     * @param orderId The ID of the order to cancel
     * @throws BusinessException if no order exists with the given ID
     */
    @Transactional
    @Override
    public void cancelOrder(UUID orderId) {
        log.info("Canceling order with ID: {}", orderId);

        OrderEntity foundOrder = getOrderById(orderId);

        log.debug("Restoring wallet balance");
        foundOrder.getWallet().setBalance(foundOrder.getWallet().getBalance().add(foundOrder.getFinalPrice()));

        log.debug("Restoring product stock for {} cart items", foundOrder.getCart().getCartItem().size());
        foundOrder.getCart().getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getProductQuantity());
        });

        orderDao.delete(foundOrder);
        log.info("Order with ID: {} canceled successfully", orderId);
    }

    /**
     * Retrieves all orders for a specific user with pagination.
     *
     * @param userId The ID of the user whose orders to retrieve
     * @param pageable Pagination information
     * @return A page of order entities for the specified user
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<OrderEntity> getOrdersByUserId(UUID userId, Pageable pageable) {
        log.info("Retrieving orders for user with ID: {}. Page: {}, Size: {}",
                userId, pageable.getPageNumber(), pageable.getPageSize());

        return orderDao.findAllByUserId(userId, pageable);
    }
}