package com.fatih.marketplace_app.entity;

import com.fatih.marketplace_app.entity.listener.InvoiceListener;
import com.fatih.marketplace_app.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "invoices")
@SQLDelete(sql = "UPDATE invoices SET record_status = true, invoice_status = 'CANCELLED' WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
@EntityListeners(InvoiceListener.class)
public class InvoiceEntity extends BaseEntity {

    @Column(name = "invoice_number", nullable = false, unique = true, length = 50)
    private String invoiceNumber;

//    @Column(name = "invoice_date", nullable = false)
//    private LocalDateTime invoiceDate;

//    @Column(name = "billing_address", nullable = false, length = 255)
//    private String billingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status", nullable = false, length = 20)
    private InvoiceStatus invoiceStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    private OrderEntity order;
}
