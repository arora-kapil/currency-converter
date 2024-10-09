package com.converter.currency.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate_tracking")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_id")
    private int trackingId;

    @Column(name = "base_currency_code")
    private String baseCurrencyCode;

    @Column(name = "quote_currency_code")
    private String quoteCurrencyCode;

    @Column(nullable = false, name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(nullable = false, name = "rate_date")
    private LocalDate rateDate;
}
