package com.toprate.test.weather.infrastructor.utils;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM = "HH:mm";

    private static DateTimeUtils instance;

    public static DateTimeUtils getInstance() {
        if (instance == null) {
            instance = new DateTimeUtils();
        }
        return instance;
    }

    public Date convertDateString(String dateString, String format) {
        // check date string
        if (!StringUtils.hasLength(dateString)) {
            return null;
        }
        // declare format
        DateFormat outSdf = new SimpleDateFormat(format);

        // parse date
        Date date;
        try {
            date = outSdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertDateString(Date date, String format) {
        // check date string
        if (date == null) {
            return null;
        }
        // declare format
        DateFormat outSdf = new SimpleDateFormat(format);

        return outSdf.format(date);
    }

}
