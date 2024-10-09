package com.converter.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateTrackingDto {
    private String baseCurrencyCode;
    private String quoteCurrencyCode;
    private BigDecimal exchangeRate;
    private LocalDate rateDate;
}
