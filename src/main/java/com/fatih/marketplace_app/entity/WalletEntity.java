package com.fatih.marketplace_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.marketplace_app.entity.listener.WalletListener;
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
@Table(name = "wallets")
@SQLDelete(sql = "UPDATE wallets SET record_status = true, balance = 0 WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(WalletListener.class)
public class WalletEntity extends BaseEntity implements Serializable {

    @Column(name = "balance", nullable = false, scale = 2, precision = 12)
    private BigDecimal balance;

    @JsonBackReference("user-wallet")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @JsonManagedReference("order-wallet")
    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
