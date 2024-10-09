package com.converter.currency.interview.util;

import com.converter.currency.util.NumberUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberUtilTest {

    @Test
    void getBigDecimal() {
        assertEquals("1.23", NumberUtil.getBigDecimal("1.234").toString());
    }
}