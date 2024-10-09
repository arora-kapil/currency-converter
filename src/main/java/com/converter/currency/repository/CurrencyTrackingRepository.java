package com.converter.currency.repository;

import com.converter.currency.data.CurrencyTrackingPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CurrencyTrackingRepository extends JpaRepository<CurrencyTrackingPair, Long> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT base_currency_code, quote_currency_code FROM CURRENCY_TRACKING_PAIR")
    List<Map<String, String>> fetchAllTrackingCurrencyPairs();
}
