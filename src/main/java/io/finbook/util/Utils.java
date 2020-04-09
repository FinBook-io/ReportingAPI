package io.finbook.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utilities {

    private static String currentUser = "11111111H";

    public static String getCurrentUser() {
        return currentUser;
    }

    public static Date getCurrentDateAndHour(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.from( localDateTime.atZone( ZoneId.systemDefault()).toInstant());
    }

    public static Date parseStringToDate(String strToDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(strToDate);
    }

    public static String formatDouble(Double numberToFormat){
        DecimalFormat df = new DecimalFormat("#,###.##");
        return df.format(numberToFormat);
    }

}
