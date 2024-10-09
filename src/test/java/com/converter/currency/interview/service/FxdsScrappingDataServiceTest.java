package com.converter.currency.interview.service;

import com.converter.currency.dto.FxdsCurrencyDataDto;
import com.converter.currency.dto.FxdsResponseDto;
import com.converter.currency.service.FxdsScrappingDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FxdsScrappingDataServiceTest {

    private final Clock clock = Clock.fixed(Instant.parse("2024-10-01T15:54:26+01:00"), Clock.systemDefaultZone().getZone());
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final FxdsScrappingDataService fxdsScrappingDataService = new FxdsScrappingDataService(restTemplate, clock);

    @Test
    void getFxdsHistoryDataSuccessfully() {
        String baseCurrencyCode = "USD";
        String quoteCurrencyCode = "INR";
        String startDate = "2021-07-01";
        String endDate = "2021-08-02";
        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=general_currency_pair&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        FxdsCurrencyDataDto fxdsCurrencyDataDto = FxdsCurrencyDataDto.builder()
                .averageAsk("1.22")
                .lowAsk("0.99")
                .highAsk("1.33")
                .baseCurrency(baseCurrencyCode)
                .quoteCurrency(quoteCurrencyCode)
                .closeTime(startDate)
                .build();
        FxdsResponseDto fxdsResponseDto = FxdsResponseDto.builder()
                .response(List.of(fxdsCurrencyDataDto))
                .build();
        Mockito.when(restTemplate.getForObject(url, FxdsResponseDto.class)).thenReturn(fxdsResponseDto);
        List<FxdsCurrencyDataDto> actual = fxdsScrappingDataService.getFxdsCurrencyHistoryDataFor(baseCurrencyCode, quoteCurrencyCode, startDate, endDate);
        assertThat(actual)
                .hasSize(1)
                .first()
                .satisfies(currencyData -> {
                    assertThat(currencyData.getBaseCurrency()).isEqualTo(baseCurrencyCode);
                    assertThat(currencyData.getQuoteCurrency()).isEqualTo(quoteCurrencyCode);
                    assertThat(currencyData.getAverageAsk()).isEqualTo("1.22");
                    assertThat(currencyData.getHighAsk()).isEqualTo("1.33");
                    assertThat(currencyData.getLowAsk()).isEqualTo("0.99");
                });


    }

    @Test
    void getFxdsTrackingDataSuccessfully() {
        String baseCurrencyCode = "USD";
        String quoteCurrencyCode = "INR";
        String startDate = "2024-07-03";
        String endDate = "2024-10-01";
        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=chart&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        FxdsCurrencyDataDto fxdsCurrencyDataDto_octoberFirst = FxdsCurrencyDataDto.builder()
                .averageAsk("1.22")
                .lowAsk("0.99")
                .highAsk("1.33")
                .baseCurrency(baseCurrencyCode)
                .quoteCurrency(quoteCurrencyCode)
                .closeTime("2024-10-01")
                .build();
        FxdsCurrencyDataDto fxdsCurrencyDataDto_octoberSecond = FxdsCurrencyDataDto.builder()
                .averageAsk("1.22")
                .lowAsk("0.99")
                .highAsk("1.33")
                .baseCurrency(baseCurrencyCode)
                .quoteCurrency(quoteCurrencyCode)
                .closeTime("2024-10-02")
                .build();
        FxdsCurrencyDataDto fxdsCurrencyDataDto_octoberThird = FxdsCurrencyDataDto.builder()
                .averageAsk("1.11")
                .lowAsk("0.99")
                .highAsk("1.55")
                .baseCurrency(baseCurrencyCode)
                .quoteCurrency(quoteCurrencyCode)
                .closeTime("2024-10-03")
                .build();

        FxdsResponseDto fxdsResponseDto = FxdsResponseDto.builder()
                .response(List.of(fxdsCurrencyDataDto_octoberFirst, fxdsCurrencyDataDto_octoberSecond, fxdsCurrencyDataDto_octoberThird))
                .build();
        Mockito.when(restTemplate.getForObject(url, FxdsResponseDto.class)).thenReturn(fxdsResponseDto);
        List<FxdsCurrencyDataDto> actual = fxdsScrappingDataService.getFxdsCurrencyTrackingDataFor(baseCurrencyCode, quoteCurrencyCode);
        assertThat(actual)
                .hasSize(3)
                .first()
                .satisfies(currencyData -> {
                    assertThat(currencyData.getBaseCurrency()).isEqualTo(baseCurrencyCode);
                    assertThat(currencyData.getQuoteCurrency()).isEqualTo(quoteCurrencyCode);
                    assertThat(currencyData.getAverageAsk()).isEqualTo("1.22");
                    assertThat(currencyData.getHighAsk()).isEqualTo("1.33");
                    assertThat(currencyData.getLowAsk()).isEqualTo("0.99");
                    assertThat(currencyData.getCloseTime()).isEqualTo("2024-10-01");
                });

    }

    @Test
    void getFxdsCurrencyHistoryDataFor_shouldReturnEmptyList_whenRestTemplateThrowsException() {
        String baseCurrencyCode = "USD";
        String quoteCurrencyCode = "INR";
        String startDate = "2021-07-01";
        String endDate = "2021-08-02";
        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=general_currency_converter_pair&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        Mockito.when(restTemplate.getForObject(url, FxdsResponseDto.class)).thenThrow(new RuntimeException("Error fetching data from URL " + url));
        List<FxdsCurrencyDataDto> actual = fxdsScrappingDataService.getFxdsCurrencyHistoryDataFor(baseCurrencyCode, quoteCurrencyCode, startDate, endDate);
        assertThat(actual).isEmpty();
    }

    @Test
    void getFxdsCurrencyTrackingDataFor_shouldReturnEmptyList_whenRestTemplateThrowsException() {
        String baseCurrencyCode = "USD";
        String quoteCurrencyCode = "INR";
        String startDate = "2024-07-03";
        String endDate = "2024-10-01";
        String url = String.format("https://fxds-public-exchange-rates-api.oanda.com/cc-api/currencies?base=%s&quote=%s&data_type=chart&start_date=%s&end_date=%s", baseCurrencyCode, quoteCurrencyCode, startDate, endDate);

        Mockito.when(restTemplate.getForObject(url, FxdsResponseDto.class)).thenThrow(new RuntimeException("Error fetching data from URL " + url));
        List<FxdsCurrencyDataDto> actual = fxdsScrappingDataService.getFxdsCurrencyTrackingDataFor(baseCurrencyCode, quoteCurrencyCode);
        assertThat(actual).isEmpty();
    }

}