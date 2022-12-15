package com.toprate.test.weather.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toprate.test.weather.api.form.WeatherCondition;
import com.toprate.test.weather.configuration.WeatherApiProperties;
import com.toprate.test.weather.domain.dto.WeatherDto;
import com.toprate.test.weather.domain.entity.Location;
import com.toprate.test.weather.domain.entity.Weather;
import com.toprate.test.weather.domain.model.DataApi;
import com.toprate.test.weather.domain.repository.LocationRepository;
import com.toprate.test.weather.domain.repository.WeatherRepository;
import com.toprate.test.weather.infrastructor.utils.DateTimeUtils;
import com.toprate.test.weather.infrastructor.utils.WeatherApiUriUtils;
import com.toprate.test.weather.service.ValidateService;
import com.toprate.test.weather.service.WeatherService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@Service
@EnableConfigurationProperties(WeatherApiProperties.class)
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherApiProperties prop;

    @Autowired
    WebClient webClient;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    ValidateService validateService;

    @Override
    public List<Weather> pullData(){
        try {
            WeatherApiUriUtils utils = WeatherApiUriUtils.getInstance();
            String url = utils.generateUrl(
                    prop.getHost(), prop.getLon(), prop.getLat(), prop.getFields(),
                    prop.getUnits(), prop.getTimeSteps(), prop.getStartTime(),
                    prop.getEndTime(), prop.getTimeZone(), prop.getApiKey()
            );
            Optional<Location> location = locationRepository.findByLatAndLon(prop.getLat(), prop.getLon());
            Location savedLocation;
            if (location.isEmpty()) {
                Location newLocation = new Location();
                newLocation.setLon(prop.getLon());
                newLocation.setLat(prop.getLat());
                savedLocation = locationRepository.save(newLocation);
            } else {
                savedLocation = location.get();
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Accept-Encoding", "identity")
                .build();
            ResponseBody responseBody = client.newCall(request).execute().body();
            // Read data from body
            ObjectMapper objectMapper = new ObjectMapper();
            String res = responseBody.string();
            DataApi dataApi = objectMapper.readValue(res, DataApi.class);
            ArrayList<Object> timelinesList = (ArrayList<Object>) dataApi.getData().get("timelines");
            LinkedHashMap<String, Object> timelines = (LinkedHashMap<String, Object>)timelinesList.get(0);
            ArrayList<Object> intervals = (ArrayList<Object>) timelines.get("intervals");
            List<Weather> result = new ArrayList<>();
            intervals.forEach(interval -> {
                LinkedHashMap<String, Object> intervalMap = (LinkedHashMap<String, Object>) interval;
                String time = (String) intervalMap.get("startTime");
                LinkedHashMap<String, Object> valueOfTemperature = (LinkedHashMap<String, Object>) intervalMap.get("values");
                Float temperature = Float.valueOf(valueOfTemperature.get("temperature").toString());
                OffsetDateTime odt = OffsetDateTime.parse(time);
                Instant instant = odt.toInstant();
                Date date = Date.from(instant);

                // Check date time with temperature is exist or not
                Optional<Weather> weather = weatherRepository.findByTimeAndLocationId(date, savedLocation.getId());
                Weather savedWeather;
                if (weather.isEmpty()) {
                    savedWeather = new Weather();
                    savedWeather.setTime(date);
                    savedWeather.setTemperature(temperature);
                    savedWeather.setLocationId(savedLocation.getId());
                } else {
                    savedWeather = weather.get();
                    savedWeather.setTime(date);
                }
                result.add(savedWeather);
            });
            // Save to database
            return weatherRepository.saveAll(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public WeatherDto getColdestAndHottest(WeatherCondition condition) {
        // Validate input
        validateService.validate(condition);
        // Declare datetimeUtils
        DateTimeUtils dateTimeUtils = DateTimeUtils.getInstance();

        List<Weather> hottest = weatherRepository.getAllHottest(condition.getDate());
        List<Weather> coldest = weatherRepository.getAllColdest(condition.getDate());
        List<String> highestTemperatureTime = new ArrayList<>();
        List<String> lowestTemperatureTime = new ArrayList<>();
        WeatherDto dto = new WeatherDto();
        if (!hottest.isEmpty()) {
            dto.setHighestTemperature(hottest.get(0).getTemperature());
            hottest.forEach(data -> {
                highestTemperatureTime.add(dateTimeUtils.convertDateString(data.getTime(), DateTimeUtils.HH_MM));
            });
        }
        if (!coldest.isEmpty()) {
            dto.setLowestTemperature(coldest.get(0).getTemperature());
            coldest.forEach(data -> {
                lowestTemperatureTime.add(dateTimeUtils.convertDateString(data.getTime(), DateTimeUtils.HH_MM));
            });
        }
        dto.setDate(condition.getDate());
        dto.setLowestTemperatureTime(lowestTemperatureTime);
        dto.setHighestTemperatureTime(highestTemperatureTime);
        return dto;
    }

}
