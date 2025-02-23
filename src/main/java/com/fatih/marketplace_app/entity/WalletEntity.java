package com.fatih.marketplace_app.entity;

import com.fatih.marketplace_app.entity.listener.WalletListener;
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
@Table(name = "wallets")
@SQLDelete(sql = "UPDATE wallets SET record_status = true, balance = 0 WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(WalletListener.class)
public class WalletEntity extends BaseEntity {

    @Column(name = "balance", nullable = false, scale = 2, precision = 12)
    private BigDecimal balance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "wallet")
    private List<OrderEntity> orders;


}
