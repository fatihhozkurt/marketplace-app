package com.fatih.marketplace_app.entity;

import com.fatih.marketplace_app.converter.JasyptAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET record_status = true WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
public class UserEntity extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 345, unique = true)
    private String email;

    @Column(name = "phone", length = 36, nullable = false, unique = true)
    private String phone;

    @Convert(converter = JasyptAttributeConverter.class)
    @Column(name = "password", nullable = false)
    private String password;

    //Checked
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private WalletEntity wallet;

    //Checked
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CartEntity cart;

    //Checked
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<InvoiceEntity> invoices;
}