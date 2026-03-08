package com.leocodes.relikd.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");

    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DISPLAY_FORMATTER);
    }

    public static String formatShortDate(LocalDateTime dateTime) {
        if (dateTime == null)
            return "";
        return dateTime.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
}
