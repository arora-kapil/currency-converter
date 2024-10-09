package com.converter.currency.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "currency_tracking_pair")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyTrackingPair {

    @Id
    @GeneratedValue
    @Column(name = "tracking_id")
    private Long trackingId;

    @Column(name = "base_currency_code")
    private String baseCurrencyCode;

    @Column(name = "quote_currency_code")
    private String quoteCurrencyCode;

    @Column(name = "created_at")
    @CreationTimestamp
    private String createdAt;
}
