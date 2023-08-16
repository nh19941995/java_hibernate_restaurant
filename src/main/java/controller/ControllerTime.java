package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ControllerTime {
    public static LocalDateTime parseDateTime(String timeStr, String dateStr) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(timeStr, timeFormatter);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, dateFormatter);

        return LocalDateTime.of(date, time);
    }

    public static String formatDateTime(int choice, LocalDateTime dateTime) {
        if (choice == 1) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            return dateTime.format(timeFormatter);
        } else if (choice == 2) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return dateTime.format(dateFormatter);
        } else if (choice == 3) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return dateTime.format(dateFormatter);
        } else {
            throw new IllegalArgumentException("Invalid choice");
        }
    }

    public static LocalDate creatLocalDateByString(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }





}
