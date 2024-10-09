package com.converter.currency.repository;

import com.converter.currency.data.CurrencyConverterPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyConverterPair, Long> {
}
