package com.converter.currency.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class NumberUtil {

    private NumberUtil() {
    }

    public static BigDecimal getBigDecimal(String value) {
        return new BigDecimal(value).setScale(2, RoundingMode.FLOOR);
    }
}
