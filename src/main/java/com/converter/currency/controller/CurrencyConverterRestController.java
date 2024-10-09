package com.converter.currency.controller;

import com.converter.currency.dto.ApiResponseDto;
import com.converter.currency.dto.CurrencyPairInfo;
import com.converter.currency.dto.ScrappingAverageDto;
import com.converter.currency.dto.ScrappingClosingDto;
import com.converter.currency.service.CurrencyConverterScrappingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/scraping")
@CrossOrigin
@Slf4j
public class CurrencyConverterRestController {

    private final CurrencyConverterScrappingService currencyConverterScrappingService;

    public CurrencyConverterRestController(CurrencyConverterScrappingService currencyConverterScrappingService) {
        this.currencyConverterScrappingService = currencyConverterScrappingService;
    }

    @GetMapping("/existing-pairs")
    @ApiResponse(description = "Get existing currency pairs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyPairInfo> getExistingCurrencyPairs() {
        log.info("Getting existing currency pairs");
        return currencyConverterScrappingService.getExistingCurrencyPairs();
    }

    @GetMapping("/average-rate")
    @ApiResponse(description = "Get average rate for currency pair for dates range")
    @Produces(MediaType.APPLICATION_JSON)
    public ScrappingAverageDto getAverageRate(@RequestParam("base") String base,
                                              @RequestParam("quote") String quote,
                                              @RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate) {
        log.info("Getting average rate for currency pair {}-{} for dates range {} to {}", base, quote, startDate, endDate);
        return currencyConverterScrappingService.getAverageForGivenDates(base, quote, startDate, endDate);
    }

    @GetMapping("/closing-rate")
    @ApiResponse(description = "Get closing rate for given pair and date")
    @Produces(MediaType.APPLICATION_JSON)
    public ScrappingClosingDto getClosingRate(@RequestParam("base") String base,
                                              @RequestParam("quote") String quote,
                                              @RequestParam("date") String date) {
        log.info("Getting closing rate for currency pair {}-{} for date {}", base, quote, date);
        return currencyConverterScrappingService.getClosingRateForGivenDate(base, quote, date);
    }

    @PostMapping("/add-pair")
    @ApiResponse(description = "Add a new currency pair")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ApiResponseDto> addCurrencyPair(@RequestParam("base") String base,
                                                          @RequestParam("quote") String quote) {
        log.info("Adding currency pair {}-{}", base, quote);
        ApiResponseDto response = currencyConverterScrappingService.addCurrencyPair(base, quote);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
