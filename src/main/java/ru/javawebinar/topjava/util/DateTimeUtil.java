package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenDate(LocalDate ld, @Nullable LocalDate start, @Nullable LocalDate end) {
        return (start == null || ld.compareTo(start) >= 0) && (end == null || ld.compareTo(end) <= 0);
    }

    public static boolean isBetweenTime(LocalTime lt, @Nullable LocalTime start, @Nullable LocalTime end) {
        return (start == null || lt.compareTo(start) >= 0) && (end == null || lt.compareTo(end) < 0);
    }

    public static LocalDate parseLocalDate(@Nullable String string) {
        return StringUtils.hasLength(string) ? LocalDate.parse(string) : null;
    }

    public static LocalTime parseLocalTime(@Nullable String string) {
        return StringUtils.hasLength(string) ? LocalTime.parse(string) : null;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

