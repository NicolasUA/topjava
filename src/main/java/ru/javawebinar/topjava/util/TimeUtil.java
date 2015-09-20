package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.Filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * GKislin
 * 07.01.2015.
 */
public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDateTime ldt, Filter filter) {
        LocalDate startDate = filter.getFromDate() == null ? LocalDate.MIN : filter.getFromDate();
        LocalDate endDate = filter.getToDate() == null ? LocalDate.MAX : filter.getToDate();
        LocalTime startTime = filter.getFromTime() == null ? LocalTime.MIN : filter.getFromTime();
        LocalTime endTime = filter.getToTime() == null ? LocalTime.MAX : filter.getToTime();
        return ldt.toLocalDate().compareTo(startDate) >= 0 && ldt.toLocalDate().compareTo(endDate) <= 0
                && ldt.toLocalTime().compareTo(startTime) >=0 && ldt.toLocalTime().compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TME_FORMATTER);
    }
}
