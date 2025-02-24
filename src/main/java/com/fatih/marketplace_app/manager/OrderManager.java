package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.OrderDao;
import com.fatih.marketplace_app.entity.*;
import com.fatih.marketplace_app.exception.BusinessException;
import com.fatih.marketplace_app.manager.service.AddressService;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.OrderService;
import com.fatih.marketplace_app.manager.service.WalletService;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class OrderManager implements OrderService {

    private final OrderDao orderDao;
    private final CartService cartService;
    private final WalletService walletService;
    private final AddressService addressService;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public OrderEntity createOrder(OrderEntity requestedOrder) {

        CartEntity foundCart = cartService.getCartById(requestedOrder.getCart().getId());
        WalletEntity foundWallet = walletService.getWalletById(requestedOrder.getWallet().getId());
        AddressEntity createdAddress = addressService.createAddress(requestedOrder.getAddress());

        requestedOrder.setUser(foundCart.getUser());

        requestedOrder.setAddress(createdAddress);

        validateWalletBalance(foundWallet, foundCart.getCartPrice());

        validateAndDeductProductStock(foundCart);

        updateWalletBalance(foundWallet, foundCart.getCartPrice());

        requestedOrder.setOrderNumber(generateRandomOrderNumber());
        requestedOrder.setFinalPrice(foundCart.getCartPrice());

        return orderDao.save(requestedOrder);
    }


    private void validateWalletBalance(WalletEntity wallet, BigDecimal cartPrice) {
        if (wallet.getBalance().compareTo(cartPrice) < 0) {
            throw new BusinessException(
                    messageSource.getMessage("backend.exceptions.WLT003",
                            new Object[]{wallet.getId()},
                            Locale.getDefault())
            );
        }
    }

    private void validateAndDeductProductStock(CartEntity cart) {
        cart.getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();
            if (product.getStockQuantity() < item.getProductQuantity()) {
                throw new BusinessException(
                        messageSource.getMessage("backend.exceptions.PRD002", new Object[]{product.getId()},
                                Locale.getDefault())
                );
            }
        });

        cart.getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() - item.getProductQuantity());
        });
    }

    private void updateWalletBalance(WalletEntity wallet, BigDecimal cartPrice) {
        wallet.setBalance(wallet.getBalance().subtract(cartPrice));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String generateRandomOrderNumber() {
        return new Random().ints(12, 0, 10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public OrderEntity getOrderByOrderNumber(String orderNumber) {
        return orderDao.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(
                        messageSource.getMessage("backend.exceptions.ORD001",
                                new Object[]{orderNumber}, Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public OrderEntity getOrderById(UUID orderId) {
        return orderDao.findById(orderId)
                .orElseThrow(() -> new BusinessException(
                        messageSource.getMessage("backend.exceptions.ORD001",
                                new Object[]{orderId}, Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<OrderEntity> getAllOrders(Pageable pageable) {
        return orderDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void cancelOrder(UUID orderId) {

        OrderEntity foundOrder = getOrderById(orderId);

        WalletEntity wallet = foundOrder.getWallet();
        wallet.setBalance(wallet.getBalance().add(foundOrder.getFinalPrice()));

        foundOrder.getCart().getCartItem().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getProductQuantity());
        });

        orderDao.delete(foundOrder);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<OrderEntity> getOrdersByUserId(UUID userId, Pageable pageable) {
        return orderDao.findAllByUserId(userId, pageable);
    }
}