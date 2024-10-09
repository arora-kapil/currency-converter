package com.converter.currency.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateUtil() {
    }

    public static String formatDate(LocalDate currentDate) {
        return currentDate.format(formatter);
    }
}
