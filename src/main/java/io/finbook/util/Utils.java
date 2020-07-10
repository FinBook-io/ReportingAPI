package io.finbook.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class Utils {

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

    public static Double formatDoubleTwoDecimals(Double number){
        return Math.round(number*100.000)/100.000;
    }

    public static String encodeStringToBase64(String textToEncode){
        return Base64.getEncoder().encodeToString(textToEncode.getBytes());
    }

    public static String doubleToStringFormat(Double amountToRound){
        return String.format("%.2f", amountToRound);
    }

}
