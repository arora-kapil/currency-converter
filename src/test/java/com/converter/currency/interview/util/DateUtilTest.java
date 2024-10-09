package com.converter.currency.interview.util;

import com.converter.currency.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {

    @Test
    void formatDate() {
        LocalDate expected = LocalDate.of(2024, 8, 1);
        String actual = DateUtil.formatDate(expected);
        assertEquals("2024-08-01", actual);
    }

}