package com.kkaysheva.task65apps.utility;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Utility
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 11.2018
 */
public class Utility {

    public static final String TAG = Utility.class.getSimpleName();

    private static final String DATE_FORMAT_OUT = "dd.MM.yyyy";
    private static final List<String> DATE_FORMAT_IN = Arrays.asList("yyyy-MM-dd", "dd-MM-yyyy");

    private static SimpleDateFormat formatterOut = new SimpleDateFormat(DATE_FORMAT_OUT);
    private static SimpleDateFormat formatterIn = new SimpleDateFormat();

    private Utility() {
        throw new AssertionError();
    }

    public static int getAge(Date date) {
        GregorianCalendar today = new GregorianCalendar();
        GregorianCalendar birthday = new GregorianCalendar();
        GregorianCalendar birthdayThisYear = new GregorianCalendar();
        birthday.setTime(date);
        birthdayThisYear.setTime(date);
        birthdayThisYear.set(Calendar.YEAR, today.get(Calendar.YEAR));
        int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if(today.getTimeInMillis() < birthdayThisYear.getTimeInMillis())
            age--;
        return age;
    }

    public static Date getDateFromString(String dateString) {
            try {
                if (dateString.charAt(2) == '-') {
                    formatterIn.applyPattern(DATE_FORMAT_IN.get(1));
                    return formatterIn.parse(dateString);
                } else {
                    formatterIn.applyPattern(DATE_FORMAT_IN.get(0));
                    return formatterIn.parse(dateString);
                }
            } catch (ParseException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
    }

    public static String getDateFormatOut(Date date) {
        return formatterOut.format(date);
    }

    public static String capitalizeString(String string) {
        return String.format("%s%s",
                string.substring(0,1).toUpperCase(),
                string.substring(1).toLowerCase());
    }
}
