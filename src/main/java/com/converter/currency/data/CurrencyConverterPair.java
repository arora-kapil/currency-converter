package com.converter.currency.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency_converter_pair")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "seq-curr-pair", initialValue = 6, allocationSize = 100)
@Getter
public class CurrencyConverterPair {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq-curr-pair")
    @Column(name = "pair_id")
    private Long pairId;

    @Column(name = "base_currency_code")
    private String baseCurrencyCode;

    @Column(name = "quote_currency_code")
    private String quoteCurrencyCode;

}
