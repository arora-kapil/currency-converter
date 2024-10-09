package com.converter.currency.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "exchange_rate_history")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;

    @Column(name = "base_currency_code")
    private String baseCurrencyCode;

    @Column(name = "quote_currency_code")
    private String quoteCurrencyCode;

    @Column(nullable = false, name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(nullable = false, name = "closing_rate")
    private BigDecimal closingRate;

    @Column(nullable = false, name = "rate_date")
    private LocalDate rateDate;

}
