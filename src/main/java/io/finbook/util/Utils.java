package io.finbook.util;

import io.finbook.model.Invoice;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class Utils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDateTime getCurrentDate(){
        return LocalDateTime.now();
    }

    public static LocalDateTime getFirstDayCurrentMonth(){
        return getCurrentDate().with(LocalTime.MIDNIGHT).withDayOfMonth(1);
    }

    public static Integer getCurrentMonth(){
        return getCurrentDate().getMonthValue();
    }

    public static Integer getCurrentYear(){
        return getCurrentDate().getYear();
    }

    public static LocalDateTime getDateOfSpecificMonth(Integer month){
        return LocalDateTime.of(getCurrentYear(), month, 1, 0, 0);
    }

    public static LocalDate parseStringToLocalDateTime(String strToLocalDateTime){
        // using a custom pattern (01/04/2014)
        // String asCustomPattern = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return LocalDate.parse(strToLocalDateTime, formatter);
    }

    public static Date parseStringToDate(String strToDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(strToDate);
    }

    public static Double formatDoubleTwoDecimals(Double number){
        return (double) Math.round(number*100)/100;
    }

    public static String encodeStringToBase64(String textToEncode){
        return Base64.getEncoder().encodeToString(textToEncode.getBytes());
    }

}
