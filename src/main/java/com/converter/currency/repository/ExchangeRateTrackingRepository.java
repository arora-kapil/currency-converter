package com.converter.currency.repository;

import com.converter.currency.data.ExchangeRateTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateTrackingRepository extends JpaRepository<ExchangeRateTracking, Long> {

    @Query(value = "select * from exchange_rate_tracking where base_currency_code = :base and quote_currency_code = :quote and rate_date between :startDate and :endDate", nativeQuery = true)
    List<ExchangeRateTracking> fetchAllTrackingData(String base, String quote, LocalDate startDate, LocalDate endDate);
}
