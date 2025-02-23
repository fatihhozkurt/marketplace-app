package com.fatih.marketplace_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "cart_items")
@SQLDelete(sql = "UPDATE cart_items SET record_status = true, cart_item_price = 0, product_quantity = 0 WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
public class CartItemEntity extends BaseEntity {

    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @Column(name = "cart_item_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal cartItemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private ProductEntity product;
}
