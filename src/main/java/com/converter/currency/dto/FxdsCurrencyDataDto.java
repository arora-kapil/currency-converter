package com.converter.currency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FxdsCurrencyDataDto {
    @JsonProperty(value = "base_currency")
    private String baseCurrency;

    @JsonProperty(value = "quote_currency")
    private String quoteCurrency;

    @JsonProperty(value = "close_time")
    private String closeTime;

    @JsonProperty(value = "average_ask")
    private String averageAsk;

    @JsonProperty(value = "high_ask")
    private String highAsk;

    @JsonProperty(value = "low_ask")
    private String lowAsk;

}
