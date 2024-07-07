package com.finale.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, formatter);
    }
}
