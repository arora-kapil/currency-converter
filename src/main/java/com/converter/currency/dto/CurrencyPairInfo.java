package com.converter.currency.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CurrencyPairInfo {
    String baseCurrencyCode;

    String quoteCurrencyCode;
}
