package com.fatih.marketplace_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fatih.marketplace_app.enums.CampaignType;
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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "campaigns")
@SQLDelete(sql = "UPDATE campaigns SET record_status = true WHERE id = ?")
@SQLRestriction("record_status <> 'true'")
public class CampaignEntity extends BaseEntity implements Serializable {

    @Column(name = "campaign_name", nullable = false, length = 50)
    private String campaignName;

    @Column(name = "campaign_description", nullable = false, length = 500)
    private String campaignDescription;

    @Column(name = "campaign_code", length = 10, nullable = false, unique = true)
    private String campaignCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "campaign_type", nullable = false, length = 20)
    private CampaignType campaignType;

    @Column(name = "discount_value", nullable = false, scale = 2, precision = 12)
    private BigDecimal discountValue;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @JsonManagedReference("campaign-cart")
    @OneToMany(mappedBy = "campaign", fetch = FetchType.LAZY)
    private List<CartEntity> cart;
}