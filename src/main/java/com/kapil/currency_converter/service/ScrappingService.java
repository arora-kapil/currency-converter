package com.kapil.currency_converter.service;

import com.kapil.currency_converter.data.CurrencyPairInfo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ScrappingService {

    private final CurrencyPairRepository currencyPairRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ScrappingDbService scrappingDbService;

    public ScrappingService(CurrencyPairRepository currencyPairRepository, ExchangeRateHistoryRepository exchangeRateHistoryRepository, ScrappingDbService scrappingDbService1) {
        this.currencyPairRepository = currencyPairRepository;
        this.exchangeRateHistoryRepository = exchangeRateHistoryRepository;
        this.scrappingDbService = scrappingDbService1;
    }

    public ScrappingAverageDto getAverageForGivenDates(String base, String quote, String startDate, String endDate) {
        Double average = exchangeRateHistoryRepository.fetchAverageForGivenDates(base, quote, startDate, endDate);
        return new ScrappingAverageDto(average);
    }

    public List<CurrencyPairInfo> getExistingCurrencyPairs() {
        List<Map<String, String>> maps = currencyPairRepository.fetchAllCurrentCurrencyPairs();

        return maps.stream()
                .map(a -> {
                    String base1 = a.get("BASE");
                    String target = a.get("TARGET");
                    return CurrencyPairInfo.builder()
                            .baseCurrencyCode(base1)
                            .quoteCurrencyCode(target)
                            .build();
                })
                .toList();
    }

    public ScrappingClosingDto getClosingRateForGivenDate(String base, String quote, String date) {
        Double v = exchangeRateHistoryRepository.fetchClosingRateForGivenDate(base, quote, date);
        return new ScrappingClosingDto(v);
    }

    public ApiResponse addCurrencyPair(String base, String quote) {
        CurrencyPair currencyPair = CurrencyPair.builder()
                .baseCurrencyCode(base)
                .quoteCurrencyCode(quote)
                .build();

        return scrappingDbService.createCurrencyPair(currencyPair);
    }
}
