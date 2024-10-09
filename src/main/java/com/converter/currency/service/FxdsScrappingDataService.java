package com.converter.currency.service;

import com.converter.currency.dto.FxdsCurrencyDataDto;
import com.converter.currency.dto.FxdsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Service
@Slf4j
public class FxdsScrappingDataService {
    private final RestTemplate restTemplate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Clock clock;

    public FxdsScrappingDataService(RestTemplate restTemplate, Clock clock) {
        this.restTemplate = restTemplate;
        this.clock = clock;
    }

    public List<FxdsCurrencyDataDto> getFxdsCurrencyHistoryDataFor(String baseCurrencyCode, String quoteCurrencyCode, String startDate, String endDate) {
        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=general_currency_pair&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        return fetchFxdsCurrencyData(url);
    }

    public List<FxdsCurrencyDataDto> getFxdsCurrencyTrackingDataFor(String baseCurrencyCode, String quoteCurrencyCode) {
        LocalDate currentDate = LocalDate.now(clock);
        String startDate = formatDate(currentDate.minusDays(90));
        String endDate = formatDate(currentDate);

        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=chart&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        return fetchFxdsCurrencyData(url);
    }

    private List<FxdsCurrencyDataDto> fetchFxdsCurrencyData(String url) {
        FxdsResponseDto response = null;
        try {
            response = restTemplate.getForObject(url, FxdsResponseDto.class);
        } catch (RuntimeException runtimeException) {
            log.error("Error fetching data from URL {}: {}", url, runtimeException.getMessage());
        }

        if (Objects.nonNull(response)) {
            return response.getResponse();
        }
        return emptyList();
    }

    private String formatDate(LocalDate currentDate) {
        return currentDate.format(formatter);
    }

}
