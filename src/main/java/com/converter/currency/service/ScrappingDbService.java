package com.converter.currency.service;

import com.converter.currency.data.*;
import com.converter.currency.dto.ApiResponseDto;
import com.converter.currency.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScrappingDbService {
    private final CurrencyPairRepository currencyPairRepository;
    private final ExchangeRateTrackingRepository exchangeRateTrackingRepository;
    private final CurrencyTrackingRepository currencyTrackingRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    public ScrappingDbService(CurrencyPairRepository currencyPairRepository, ExchangeRateTrackingRepository exchangeRateTrackingRepository, CurrencyTrackingRepository currencyTrackingRepository, ExchangeRateHistoryRepository exchangeRateHistoryRepository) {
        this.currencyPairRepository = currencyPairRepository;
        this.exchangeRateTrackingRepository = exchangeRateTrackingRepository;
        this.currencyTrackingRepository = currencyTrackingRepository;
        this.exchangeRateHistoryRepository = exchangeRateHistoryRepository;
    }

    public ApiResponseDto createCurrencyPair(CurrencyConverterPair currencyConverterPair) {
        try {
            currencyPairRepository.save(currencyConverterPair);
            return ApiResponseDto.builder()
                    .statusCode(200)
                    .message("Currency pair created successfully")
                    .build();
        } catch (DataAccessException e) {
            log.error("Error saving currency pair: {}", e.getMessage());
            return ApiResponseDto.builder()
                    .statusCode(500)
                    .message("Failed to create currency pair due to database error")
                    .build();
        }
    }

    public ApiResponseDto addTrackingPair(CurrencyTrackingPair currencyPair, List<ExchangeRateTracking> exchangeRateTrackings) {
        try {
            currencyTrackingRepository.save(currencyPair);
            exchangeRateTrackingRepository.saveAll(exchangeRateTrackings);
            return ApiResponseDto.builder()
                    .statusCode(200)
                    .message("Tracking pair added successfully")
                    .build();
        } catch (DataAccessException e) {
            log.error("Error creating new tracking pair: {}", e.getMessage());
            return ApiResponseDto.builder()
                    .statusCode(500)
                    .message("Failed to add tracking pair due to database error")
                    .build();
        }
    }

    public void saveExchangeRateHistories(List<ExchangeRateHistory> exchangeRateHistories) {
        //surround this with try catch block
        try {
            exchangeRateHistoryRepository.saveAll(exchangeRateHistories);
        } catch (DataAccessException e) {
            log.error("Error saving exchange rate histories: {}", e.getMessage());
        }
    }
}
