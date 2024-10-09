package com.converter.currency.scheduler;

import com.converter.currency.data.ExchangeRateHistory;
import com.converter.currency.dto.CurrencyPairInfo;
import com.converter.currency.dto.FxdsCurrencyDataDto;
import com.converter.currency.service.CurrencyConverterScrappingService;
import com.converter.currency.service.FxdsScrappingDataService;
import com.converter.currency.service.ScrappingDbService;
import com.converter.currency.util.DateUtil;
import com.converter.currency.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ScrappingDataSchedulerService implements ApplicationListener<ApplicationReadyEvent> {
    private final LocalDate startDate = LocalDate.of(2024, 10, 1);
    private final FxdsScrappingDataService fxdsScrappingDataService;
    private final CurrencyConverterScrappingService currencyConverterScrappingService;
    private final ScrappingDbService scrappingDbService;

    public ScrappingDataSchedulerService(FxdsScrappingDataService fxdsScrappingDataService, CurrencyConverterScrappingService currencyConverterScrappingService, ScrappingDbService scrappingDbService) {
        this.fxdsScrappingDataService = fxdsScrappingDataService;
        this.currencyConverterScrappingService = currencyConverterScrappingService;
        this.scrappingDbService = scrappingDbService;
    }

    // This method is called when the application is ready
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LocalDate currentDate = LocalDate.now();
        log.info("Application started, fetching data from {} to {}", startDate, currentDate);
        LocalDate loopDate = startDate;
        List<CurrencyPairInfo> existingCurrencyPairs = currencyConverterScrappingService.getExistingCurrencyPairs();
        while (loopDate.isBefore(currentDate)) {
            String startDateStr = DateUtil.formatDate(loopDate);
            String endDateStr = DateUtil.formatDate(loopDate.plusDays(1));

            saveExchangeRateHistoryDataToDb(existingCurrencyPairs, startDateStr, endDateStr);

            loopDate = loopDate.plusDays(1);
        }
        log.info("Data fetched successfully");
    }

    // This method is scheduled to run at 9:00 AM every day
    @Scheduled(cron = "0 0 9 ? * *")
    public void fetchScrappingData() {
        log.info("Fetching data for yesterday");
        List<CurrencyPairInfo> existingCurrencyPairs = currencyConverterScrappingService.getExistingCurrencyPairs();

        LocalDate now = LocalDate.now();
        String startDateString = DateUtil.formatDate(now.minusDays(1));
        String endDateString = DateUtil.formatDate(now);

        saveExchangeRateHistoryDataToDb(existingCurrencyPairs, startDateString, endDateString);
    }

    private LocalDate mapRateDate(String closeTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(closeTime, inputFormatter);
        return zonedDateTime.toLocalDate();
    }

    private void saveExchangeRateHistoryDataToDb(List<CurrencyPairInfo> existingCurrencyPairs, String startDateString, String endDateString) {
        existingCurrencyPairs
                .forEach(currencyPairInfo -> {
                    String baseCurrencyCode = currencyPairInfo.getBaseCurrencyCode();
                    String quoteCurrencyCode = currencyPairInfo.getQuoteCurrencyCode();
                    List<FxdsCurrencyDataDto> fxdsCurrencyHistoryData = fxdsScrappingDataService.getFxdsCurrencyHistoryDataFor(baseCurrencyCode, quoteCurrencyCode, startDateString, endDateString);
                    List<ExchangeRateHistory> exchangeRateHistories = exchangeRateHistories(fxdsCurrencyHistoryData, baseCurrencyCode, quoteCurrencyCode);
                    log.debug("Saving exchange rate history for {}-{} for date {}", baseCurrencyCode, quoteCurrencyCode, startDateString);
                    scrappingDbService.saveExchangeRateHistories(exchangeRateHistories);
                });
    }

    private List<ExchangeRateHistory> exchangeRateHistories(List<FxdsCurrencyDataDto> fxdsCurrencyData, String baseCurrencyCode, String quoteCurrencyCode) {
        return fxdsCurrencyData.stream()
                .map(rate -> ExchangeRateHistory.builder()
                        .baseCurrencyCode(baseCurrencyCode)
                        .quoteCurrencyCode(quoteCurrencyCode)
                        .exchangeRate(NumberUtil.getBigDecimal(rate.getHighAsk()))
                        .closingRate(NumberUtil.getBigDecimal(rate.getLowAsk()))
                        .rateDate(mapRateDate(rate.getCloseTime()))
                        .build())
                .toList();
    }
}
