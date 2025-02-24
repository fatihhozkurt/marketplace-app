package com.fatih.marketplace_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.marketplace_app.entity.listener.OrderListener;
import com.fatih.marketplace_app.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET record_status = true, order_status = 'CANCELLED' WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(OrderListener.class)
public class OrderEntity extends BaseEntity implements Serializable {

    @Column(name = "order_number", nullable = false, length = 12, unique = true)
    private String orderNumber;

    @Column(name = "final_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal finalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 10)
    private OrderStatus orderStatus;

    @JsonBackReference("cart-order")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private CartEntity cart;

    @JsonManagedReference("invoice-order")
    @OneToOne(mappedBy = "order")
    private InvoiceEntity invoice;

    @JsonBackReference("order-wallet")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id", nullable = false)
    private WalletEntity wallet;

    @JsonBackReference("order-user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @JsonBackReference("address-orders")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private AddressEntity address;
}
