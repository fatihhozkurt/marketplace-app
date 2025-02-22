package com.fatih.marketplace_app.entity;

import com.fatih.marketplace_app.entity.listener.ProductListener;
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
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET record_status = true WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(ProductListener.class)
public class ProductEntity extends BaseEntity {

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Column(name = "product_description", nullable = false, length = 500)
    private String productDescription;

    @Column(name = "product_price", nullable = false, precision = 6, scale = 2)
    private BigDecimal productPrice;

    @Column(name = "stock_quantity", nullable = false)
    private Long stockQuantity;

    //Checked
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems;
}