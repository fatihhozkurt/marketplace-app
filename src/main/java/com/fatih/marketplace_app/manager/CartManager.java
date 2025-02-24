package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CartDao;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.entity.UserEntity;
import com.fatih.marketplace_app.exception.DataAlreadyExistException;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.fatih.marketplace_app.constant.RecordStatus.PASSIVE;

@Service
@RequiredArgsConstructor
public class CartManager implements CartService {

    private final CartDao cartDao;
    private final UserService userService;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public CartEntity createCart(CartEntity cartEntity) {

        UserEntity foundUser = userService.getUserById(cartEntity.getUser().getId());

        if (foundUser.getCart() != null) {
            throw new DataAlreadyExistException(messageSource
                    .getMessage("backend.exceptions.CRT003",
                            new Object[]{},
                            Locale.getDefault()));
        }
        return cartDao.save(cartEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartEntity getCartById(UUID cartId) {
        return cartDao.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource
                        .getMessage("backend.exceptions.CRT001",
                                new Object[]{cartId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartEntity getCartByUserId(UUID userId) {

        userService.getUserById(userId);
        return cartDao.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(messageSource.getMessage("backend.exceptions.CRT002",
                                new Object[]{userId},
                                Locale.getDefault())));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartEntity> getAllCarts(Pageable pageable) {
        return cartDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteCart(UUID cartId) {
        CartEntity foundCart = getCartById(cartId);
        cartDao.delete(foundCart);
    }

    @Transactional
    @Override
    public void clearCart(UUID cartId) {
        CartEntity foundCart = getCartById(cartId);
        foundCart.getCartItem().clear();
        foundCart.setCartPrice(BigDecimal.ZERO);
        cartDao.save(foundCart);
    }

    @Transactional
    @Override
    public CartEntity updateCart(CartEntity requestedCart) {

        CartEntity foundCart = getCartById(requestedCart.getId());
        CartEntity updatedCart = checkCartConditions(foundCart, requestedCart);

        return cartDao.save(updatedCart);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CartEntity checkCartConditions(CartEntity foundCart, CartEntity requestedCart) {

        if (requestedCart.getCartPrice() != null) {
            foundCart.setCartPrice(requestedCart.getCartPrice());
        }
        return foundCart;
    }

    @Scheduled(cron = "0 0/15 * * * ?")
    @Transactional
    public void cleanUpExpiredCarts() {
        LocalDateTime expirationTime = LocalDateTime.now().minusMinutes(15);
        List<CartEntity> expiredCarts = cartDao.findByUpdateTimeBefore(expirationTime);

        if (!expiredCarts.isEmpty()) {
            expiredCarts.forEach(cartEntity -> {
                if (cartEntity.getCartItem() != null) {
                    cartEntity.getCartItem().clear();
                    cartEntity.setCartPrice(BigDecimal.ZERO);
                    cartDao.save(cartEntity);
                }
            });
        }
    }
}
