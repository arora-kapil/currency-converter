package com.converter.currency.controller;

import com.converter.currency.dto.ApiResponseDto;
import com.converter.currency.dto.CurrencyPairInfo;
import com.converter.currency.dto.ExchangeRateTrackingDto;
import com.converter.currency.service.CurrencyTrackingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/tracking")
@CrossOrigin
@Slf4j
public class TrackingRestController {
    private final CurrencyTrackingService currencyTrackingService;

    public TrackingRestController(CurrencyTrackingService currencyTrackingService) {
        this.currencyTrackingService = currencyTrackingService;
    }


    @GetMapping("/get-existing-pairs")
    @ApiResponse(description = "Get tracking data for a given pair")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyPairInfo> getExistingTrackingPairs() {
        log.info("Getting existing tracking pairs");

        return currencyTrackingService.getExistingTrackingPairs();
    }

    @GetMapping("/get-data")
    @ApiResponse(description = "Get tracking data for a given pair")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExchangeRateTrackingDto> getTrackingData(@RequestParam("base") String base,
                                                         @RequestParam("quote") String quote) {
        log.info("Getting tracking data for currency pair {}-{}", base, quote);

        return currencyTrackingService.getTrackingData(base, quote);
    }

    @PostMapping("/add-pair")
    @ApiResponse(description = "Add a currency pair to track the data")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<ApiResponseDto> addTracking(@RequestParam("base") String base,
                                                      @RequestParam("quote") String quote) {
        log.info("Adding tracking for currency pair {}-{}", base, quote);

        ApiResponseDto response = currencyTrackingService.addTrackingPair(base, quote);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
}
