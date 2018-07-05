package org.congesapp.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tools {
    public static Date randomDate() {

        //Time between epoch and 31/12/2000
        long time = (long) (Math.random() * 978217200000L);
        return new Date(time);
    }

    public static Date strToDate(String str) throws ParseException {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
        Date date = format.parse(str);

        return date;
    }

    public static Date addDays(Calendar cal, Integer days) {

        Calendar cal2 = (Calendar) cal.clone();
        cal2.add(Calendar.DAY_OF_MONTH, days);
        return cal2.getTime();
    }
}
