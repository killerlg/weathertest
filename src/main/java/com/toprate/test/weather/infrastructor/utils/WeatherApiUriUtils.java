package com.toprate.test.weather.infrastructor.utils;

import java.util.Date;

public class WeatherApiUriUtils {

    // instance
    private static WeatherApiUriUtils instance;

    // get instance
    public static WeatherApiUriUtils getInstance() {
        if (instance == null) {
            instance = new WeatherApiUriUtils();
        }
        return instance;
    }

    public String generateUrl(String host, String lon, String lat, String fields, String units, String timeSteps,
                              String startTime, String endTime, String timeZone, String apiKey) {

        StringBuilder uriBuilder = new StringBuilder(host)
                .append("/v4/timelines?")
                .append("location=" + lon + "%2C%20" + lat)
                .append("&fields="+ fields)
                .append("&units=" + units)
                .append("&timesteps=" +timeSteps)
                .append("&startTime=" + startTime)
                .append("&endTime=" + endTime)
                .append("&timezone=" + timeZone)
                .append("&apikey=" + apiKey);

        return uriBuilder.toString();
    }

}
