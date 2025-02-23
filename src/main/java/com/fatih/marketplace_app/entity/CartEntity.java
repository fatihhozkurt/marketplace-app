package com.fatih.marketplace_app.entity;

import com.fatih.marketplace_app.entity.listener.CartListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
public class CartEntity extends BaseEntity {


    @Column(name = "cart_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal cartPrice;

    //Checked
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    //Checked
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItem;

    //Checked
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id", referencedColumnName = "id")
    private CampaignEntity campaign;

    @OneToOne(mappedBy = "cart")
    private OrderEntity order;
}
