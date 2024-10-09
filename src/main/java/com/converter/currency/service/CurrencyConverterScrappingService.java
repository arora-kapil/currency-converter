package com.converter.currency.service;

import com.converter.currency.data.CurrencyConverterPair;
import com.converter.currency.dto.ApiResponseDto;
import com.converter.currency.dto.CurrencyPairInfo;
import com.converter.currency.dto.ScrappingAverageDto;
import com.converter.currency.dto.ScrappingClosingDto;
import com.converter.currency.model.CurrencyEnum;
import com.converter.currency.repository.CurrencyPairRepository;
import com.converter.currency.repository.ExchangeRateHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CurrencyConverterScrappingService {
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;
    private final ScrappingDbService scrappingDbService;
    private final CurrencyPairRepository currencyPairRepository;


    public CurrencyConverterScrappingService(ExchangeRateHistoryRepository exchangeRateHistoryRepository, ScrappingDbService scrappingDbService1, CurrencyPairRepository currencyPairRepository) {
        this.exchangeRateHistoryRepository = exchangeRateHistoryRepository;
        this.scrappingDbService = scrappingDbService1;
        this.currencyPairRepository = currencyPairRepository;
    }

    public List<CurrencyPairInfo> getExistingCurrencyPairs() {
        List<CurrencyConverterPair> currencyConverterPairs = currencyPairRepository.findAll();
        return currencyConverterPairs.stream()
                .map(currencyPair -> {
                    String baseCurrency = currencyPair.getBaseCurrencyCode();
                    String quoteCurrency = currencyPair.getQuoteCurrencyCode();
                    return CurrencyPairInfo.builder()
                            .baseCurrencyCode(baseCurrency)
                            .quoteCurrencyCode(quoteCurrency)
                            .build();
                }).toList();
    }

    public ScrappingAverageDto getAverageForGivenDates(String base, String quote, String startDate, String endDate) {
        Double average = exchangeRateHistoryRepository.fetchAverageForGivenDates(base, quote, startDate, endDate);
        return new ScrappingAverageDto(average);
    }

    public ScrappingClosingDto getClosingRateForGivenDate(String base, String quote, String date) {
        Double v = exchangeRateHistoryRepository.fetchClosingRateForGivenDate(base, quote, date);
        return new ScrappingClosingDto(v);
    }

    public ApiResponseDto addCurrencyPair(String base, String quote) {
        if (CurrencyEnum.isValidCurrency(base) && CurrencyEnum.isValidCurrency(quote)) {
            return createCurrencyPair(base, quote);
        }
        return new ApiResponseDto(400, "Invalid currency code");
    }

    private ApiResponseDto createCurrencyPair(String base, String quote) {
        CurrencyConverterPair currencyConverterPair = CurrencyConverterPair.builder()
                .baseCurrencyCode(base)
                .quoteCurrencyCode(quote)
                .build();

        return scrappingDbService.createCurrencyPair(currencyConverterPair);
    }
}

