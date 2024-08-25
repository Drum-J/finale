package com.finale.date;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateTest {

    @Test
    void date() throws Exception {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = "2024-07-01";
        LocalDate originalDate = LocalDate.parse(date, formatter);

        DayOfWeek dayOfWeek = DayOfWeek.of(7);
        System.out.println("dayOfWeek = " + dayOfWeek);

        //when
        LocalDate nextMonthFirstDay = originalDate.plusMonths(1).withDayOfMonth(1);
        System.out.println("nextMonthFirstDay = " + nextMonthFirstDay);

        LocalDate newDate = nextMonthFirstDay.with(TemporalAdjusters.nextOrSame(dayOfWeek));

        //then
        for (int i = 0; i < 4; i++) {
            LocalDate weeks = newDate.plusWeeks(i);
            System.out.println("weeks["+i+"] = " + weeks);
        }
    }
}
