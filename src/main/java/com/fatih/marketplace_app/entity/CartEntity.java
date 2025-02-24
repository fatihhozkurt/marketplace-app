package com.fatih.marketplace_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.marketplace_app.entity.listener.CartListener;
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
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "carts")
@SQLDelete(sql = "UPDATE carts SET record_status = true, cart_price = 0 WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(CartListener.class)
public class CartEntity extends BaseEntity implements Serializable {


    @Column(name = "cart_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal cartPrice;

    @JsonBackReference("cart-user")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @JsonManagedReference("cart-cartItem")
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItem;

    @JsonBackReference("campaign-cart")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaign;

    @JsonManagedReference("cart-order")
    @OneToOne(mappedBy = "cart")
    private OrderEntity order;
}
