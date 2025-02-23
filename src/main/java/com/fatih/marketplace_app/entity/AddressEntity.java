package com.fatih.marketplace_app.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "addresses")
@SQLDelete(sql = "UPDATE addresses SET record_status = true WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
public class AddressEntity extends BaseEntity {

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "neighbourhood", nullable = false, length = 100)
    private String neighbourhood;

    @Column(name = "street", length = 100, nullable = false)
    private String street;

    @Column(name = "apartment", length = 100, nullable = false)
    private String apartment;

    @Column(name = "apartment_number", length = 6, nullable = false)
    private String apartmentNumber;

    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @OneToMany(mappedBy = "address")
    private List<OrderEntity> orders;
}
