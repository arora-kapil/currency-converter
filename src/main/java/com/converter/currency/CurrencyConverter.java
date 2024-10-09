package com.converter.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.converter.currency"})  // scan JPA entities

public class CurrencyConverter {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverter.class, args);
    }

}
