package com.converter.currency.repository;

import com.converter.currency.data.CurrencyConverterPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyConverterPair, Long> {
}
