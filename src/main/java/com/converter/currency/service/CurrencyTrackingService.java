package com.converter.currency.service;

import com.converter.currency.data.CurrencyTrackingPair;
import com.converter.currency.data.ExchangeRateTracking;
import com.converter.currency.dto.ApiResponseDto;
import com.converter.currency.dto.CurrencyPairInfo;
import com.converter.currency.dto.ExchangeRateTrackingDto;
import com.converter.currency.dto.FxdsCurrencyDataDto;
import com.converter.currency.repository.CurrencyTrackingRepository;
import com.converter.currency.repository.ExchangeRateTrackingRepository;
import com.converter.currency.util.NumberUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyTrackingService {
    private static final String BASE = "BASE_CURRENCY_CODE";
    private static final String QUOTE = "QUOTE_CURRENCY_CODE";
    private final FxdsScrappingDataService fxdsScrappingDataService;
    private final ExchangeRateTrackingRepository exchangeRateTrackingRepository;
    private final ScrappingDbService scrappingDbService;
    private final CurrencyTrackingRepository currencyTrackingRepository;

    public CurrencyTrackingService(FxdsScrappingDataService fxdsScrappingDataService, ExchangeRateTrackingRepository exchangeRateTrackingRepository, ScrappingDbService scrappingDbService, CurrencyTrackingRepository currencyTrackingRepository) {
        this.fxdsScrappingDataService = fxdsScrappingDataService;
        this.exchangeRateTrackingRepository = exchangeRateTrackingRepository;
        this.scrappingDbService = scrappingDbService;
        this.currencyTrackingRepository = currencyTrackingRepository;
    }

    public List<CurrencyPairInfo> getExistingTrackingPairs() {
        List<Map<String, String>> currentCurrencyPairs = currencyTrackingRepository.fetchAllTrackingCurrencyPairs();
        return currentCurrencyPairs.stream()
                .map(currencyPair -> {
                    String base = currencyPair.get(BASE);
                    String target = currencyPair.get(QUOTE);
                    return CurrencyPairInfo.builder()
                            .baseCurrencyCode(base)
                            .quoteCurrencyCode(target)
                            .build();
                })
                .toList();
    }

    public ApiResponseDto addTrackingPair(String base, String quote) {

        CurrencyTrackingPair currencyPairForTracking = CurrencyTrackingPair.builder()
                .baseCurrencyCode(base)
                .quoteCurrencyCode(quote)
                .build();

        List<FxdsCurrencyDataDto> fxdsCurrencyTrackingDataFor = fxdsScrappingDataService.getFxdsCurrencyTrackingDataFor(base, quote);
        List<ExchangeRateTracking> exchangeRateTracking = mapExchangeRateTrackings(base, quote, fxdsCurrencyTrackingDataFor);

        return scrappingDbService.addTrackingPair(currencyPairForTracking, exchangeRateTracking);
    }

    private List<ExchangeRateTracking> mapExchangeRateTrackings(String base, String quote, List<FxdsCurrencyDataDto> fxdsCurrencyTrackingDataFor) {
        return fxdsCurrencyTrackingDataFor.stream()
                .map(rate -> ExchangeRateTracking.builder()
                        .baseCurrencyCode(base)
                        .quoteCurrencyCode(quote)
                        .exchangeRate(mapExchangeRate(rate))
                        .rateDate(mapRateDate(rate.getCloseTime()))
                        .build())
                .toList();
    }

    private static BigDecimal mapExchangeRate(FxdsCurrencyDataDto rate) {
        if (rate.getHighAsk() == null) {
            return NumberUtil.getBigDecimal(rate.getAverageAsk());
        }
        return NumberUtil.getBigDecimal(rate.getHighAsk());
    }

    private LocalDate mapRateDate(String closeTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(closeTime, inputFormatter);
        return zonedDateTime.toLocalDate();
    }

    public List<ExchangeRateTrackingDto> getTrackingData(String base, String quote) {
        LocalDate startDate = LocalDate.now().minusDays(90);
        LocalDate endDate = LocalDate.now();
        List<ExchangeRateTracking> exchangeRateTrackings = exchangeRateTrackingRepository.fetchAllTrackingData(base, quote, startDate, endDate);
        return exchangeRateTrackings.stream()
                .map(ert -> ExchangeRateTrackingDto.builder()
                        .baseCurrencyCode(ert.getBaseCurrencyCode())
                        .quoteCurrencyCode(ert.getQuoteCurrencyCode())
                        .exchangeRate(ert.getExchangeRate())
                        .rateDate(ert.getRateDate())
                        .build())
                .toList();

    }
}
