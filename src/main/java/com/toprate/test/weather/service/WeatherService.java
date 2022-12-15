package com.toprate.test.weather.service;

import com.toprate.test.weather.api.form.WeatherCondition;
import com.toprate.test.weather.domain.dto.WeatherDto;
import com.toprate.test.weather.domain.entity.Weather;

import java.util.Date;
import java.util.List;

public interface WeatherService {
    List<Weather> pullData();

    WeatherDto getColdestAndHottest(WeatherCondition condition);

}
