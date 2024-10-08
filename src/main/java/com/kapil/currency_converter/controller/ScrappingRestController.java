package com.kapil.currency_converter.controller;

import com.kapil.currency_converter.data.CurrencyPairInfo;
import com.kapil.currency_converter.service.ScrappingService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping
@CrossOrigin
public class ScrappingRestController {

    private final ScrappingService scrappingService;

    public ScrappingRestController(ScrappingService scrappingService) {
        this.scrappingService = scrappingService;
    }

    @GetMapping("/existing-pairs")
    @ApiResponse(description = "Get existing currency pairs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CurrencyPairInfo> getExistingCurrencyPairs() {
        return scrappingService.getExistingCurrencyPairs();
    }

    @GetMapping("/average-rate")
    public ScrappingAverageDto getAverageRate(@RequestParam("base") String base,
                                              @RequestParam("quote") String quote,
                                              @RequestParam("startDate") String startDate,
                                              @RequestParam("endDate") String endDate) {
        return scrappingService.getAverageForGivenDates(base, quote, startDate, endDate);
    }

    @GetMapping("/closing-rate")
    public ScrappingClosingDto getClosingRate(@RequestParam("base") String base,
                                              @RequestParam("quote") String quote,
                                              @RequestParam("date") String date) {
        return scrappingService.getClosingRateForGivenDate(base,quote, date);
    }

    public ResponsEntity<com.kapil.currency_converter.dto.ApiResponse> addCurrencyPair(@RequestParam("base") String base,
                                                                                       @RequestParam("quote") String quote) {
        com.kapil.currency_converter.dto.ApiResponse response = scrappingService.addCurrencyPair(base, quote);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
