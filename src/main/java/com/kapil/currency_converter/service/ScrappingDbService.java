package com.kapil.currency_converter.service;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class ScrappingDbService {
    private final CurrencypairRespository currencypairRespository;
    private final ExchangeRateTrackingRepository exchangeRateTrackingRepository;
    private final CurrencyTrackingRepository currencyTrackingRepository;
    private final ExchangeRateHistoryRepository exchangeRateHistoryRepository;

    public ScrappingDbService(CurrencypairRespository currencypairRespository, ExchangeRateTrackingRepository exchangeRateTrackingRepository, CurrencyTrackingRepository currencyTrackingRepository, ExchangeRateHistoryRepository exchangeRateHistoryRepository) {
        this.currencypairRespository = currencypairRespository;
        this.exchangeRateTrackingRepository = exchangeRateTrackingRepository;
        this.currencyTrackingRepository = currencyTrackingRepository;
        this.exchangeRateHistoryRepository = exchangeRateHistoryRepository;
    }

    @Transactional
    public ApiResponse createCurrencyRepair(CurrencyPair currencyPair) {
        try {
            currencypairRespository.save(currencyPair);
            return ApiResponse.builder()
                    .statusCode(200)
                    .message("Currency pair created successfully")
                    .build();
        } catch (DataAccessException e) {
            log.error("Error saving currency pair: ", e.getMessage());
            return ApiResponse.builder()
                    .statusCode(500)
                    .message("Failed to create currency pair due to database error")
                    .build();
        }
    }

    @Transactional
    public ApiResponse addTrackingPair(CurrencyTracking currencyTracking, List<ExchangeRateTracking> exchangeRateTrackings) {
        try {
            currencyTrackingRepository.save(currencyTracking);
            exchangeRateTrackingRepository.saveAll(exchangeRateTrackings);
            return ApiResponse.builder()
                    .statusCode(200)
                    .message("Tracking pair added successfully")
                    .build();
        } catch (DataAccessException e) {
            log.error("Error creating new tracking pair: {}", e.getMessage());
            return ApiResponse.builder()
                    .statusCode(500)
                    .message("Failed to add tracking pair due to database error")
                    .build();
        }
    }

    @Transactional
    public void saveExchangeRateHistories(List<ExchangeRateHistory> exchangeRateHistories) {
        exchangeRateHistoryRepository.saveAll(exchangeRateHistories);
    }
}
