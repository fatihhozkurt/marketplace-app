package com.fatih.marketplace_app.manager;

import com.fatih.marketplace_app.dao.CartItemDao;
import com.fatih.marketplace_app.entity.CartEntity;
import com.fatih.marketplace_app.entity.CartItemEntity;
import com.fatih.marketplace_app.entity.ProductEntity;
import com.fatih.marketplace_app.exception.ResourceNotFoundException;
import com.fatih.marketplace_app.manager.service.CartItemService;
import com.fatih.marketplace_app.manager.service.CartService;
import com.fatih.marketplace_app.manager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartItemManager implements CartItemService {

    private final CartItemDao cartItemDao;
    private final MessageSource messageSource;
    private final CartService cartService;
    private final ProductService productService;

    @Transactional
    @Override
    public CartItemEntity createCartItem(CartItemEntity requestedCartItem) {
        ProductEntity foundProduct = productService.getProductById(requestedCartItem.getProduct().getId());
        CartEntity foundCart = cartService.getCartById(requestedCartItem.getCart().getId());

        Optional<CartItemEntity> matchingCartItemOpt = findMatchingCartItem(foundCart, foundProduct);

        if (matchingCartItemOpt.isPresent()) {
            CartItemEntity existingCartItem = matchingCartItemOpt.get();
            updateExistingCartItem(existingCartItem, requestedCartItem.getProductQuantity(), foundProduct.getProductPrice());
            return cartItemDao.save(existingCartItem);
        } else {
            BigDecimal newCartItemPrice = foundProduct.getProductPrice()
                    .multiply(BigDecimal.valueOf(requestedCartItem.getProductQuantity()));
            requestedCartItem.setCartItemPrice(newCartItemPrice);
            foundCart.setCartPrice(foundCart.getCartPrice().add(newCartItemPrice));
            return cartItemDao.save(requestedCartItem);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Optional<CartItemEntity> findMatchingCartItem(CartEntity cart, ProductEntity product) {
        return cart.getCartItem().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void updateExistingCartItem(CartItemEntity existingCartItem, int additionalQuantity, BigDecimal productPrice) {
        int updatedQuantity = existingCartItem.getProductQuantity() + additionalQuantity;
        existingCartItem.setProductQuantity(updatedQuantity);
        existingCartItem.setCartItemPrice(
                productPrice.multiply(BigDecimal.valueOf(updatedQuantity))
        );
        existingCartItem.getCart().setCartPrice(existingCartItem.getCart().getCartPrice().add(existingCartItem.getCartItemPrice()));
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public CartItemEntity getCartItemById(UUID cartItemId) {
        return cartItemDao.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(
                        "backend.exceptions.CIT001",
                        new Object[]{cartItemId},
                        Locale.getDefault())));
    }

    @Transactional
    @Override
    public CartItemEntity updateCartItem(CartItemEntity requestedCartItem) {
        CartItemEntity foundCartItem = getCartItemById(requestedCartItem.getId());

        CartItemEntity updatedCartItem = checkCartItemConditions(foundCartItem, requestedCartItem);

        return cartItemDao.save(updatedCartItem);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartItemEntity> getAllCartItems(Pageable pageable) {
        return cartItemDao.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteCartItem(UUID cartItemId) {
        CartItemEntity foundCartItem = getCartItemById(cartItemId);
        cartItemDao.delete(foundCartItem);
    }

    @Transactional
    @Override
    public CartItemEntity removeProductFromCartItem(UUID cartItemId, UUID productId) {

        productService.getProductById(productId);
        CartItemEntity foundCartItem = getCartItemById(cartItemId);

        if (foundCartItem.getProductQuantity() <= 0) {
            deleteCartItem(cartItemId);
        }

        foundCartItem.setProductQuantity(foundCartItem.getProductQuantity() - 1);
        foundCartItem.setCartItemPrice(
                foundCartItem.getProduct().getProductPrice().multiply(
                        BigDecimal.valueOf(foundCartItem.getProductQuantity())
                )
        );

        return cartItemDao.save(foundCartItem);
    }


    @Transactional
    @Override
    public CartItemEntity addProductToCartItem(UUID cartItemId, UUID productId) {

        productService.getProductById(productId);
        CartItemEntity foundCartItem = getCartItemById(cartItemId);

        foundCartItem.setProductQuantity(foundCartItem.getProductQuantity() + 1);
        foundCartItem.setCartItemPrice(
                foundCartItem.getProduct().getProductPrice().multiply(
                        BigDecimal.valueOf(foundCartItem.getProductQuantity())
                )
        );

        return cartItemDao.save(foundCartItem);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<CartItemEntity> getCartItemsByCartId(UUID cartId, Pageable pageable) {
        cartService.getCartById(cartId);
        return cartItemDao.findAllByCartId(cartId, pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CartItemEntity checkCartItemConditions(CartItemEntity foundCartItem, CartItemEntity requestedCartItem) {
        if (requestedCartItem.getProductQuantity() != null) {
            foundCartItem.setProductQuantity(requestedCartItem.getProductQuantity());
            foundCartItem.setCartItemPrice(foundCartItem.getProduct().getProductPrice().multiply(BigDecimal.valueOf(requestedCartItem.getProductQuantity())));
        }
        if (requestedCartItem.getCartItemPrice() != null) {
            foundCartItem.setCartItemPrice(requestedCartItem.getCartItemPrice());
        }
        return foundCartItem;
    }
}
