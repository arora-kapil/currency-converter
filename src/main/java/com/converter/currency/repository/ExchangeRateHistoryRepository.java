package com.converter.currency.repository;

import com.converter.currency.data.ExchangeRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateHistoryRepository extends JpaRepository<ExchangeRateHistory, Long> {
    @Query(nativeQuery = true, value = "SELECT AVG(exchange_rate) AS average_rate " +
            "FROM EXCHANGE_RATE_HISTORY " +
            "WHERE base_currency_code = :base " +
            "  AND quote_currency_code = :quote " +
            "  AND rate_date BETWEEN :startDate AND :endDate")
    Double fetchAverageForGivenDates(String base, String quote, String startDate, String endDate);

    @Query(nativeQuery = true, value = "SELECT closing_rate " +
            "FROM EXCHANGE_RATE_HISTORY " +
            "WHERE base_currency_code = :base " +
            "  AND quote_currency_code = :quote " +
            "  AND rate_date = :date")
    Double fetchClosingRateForGivenDate(String base, String quote, String date);
}
