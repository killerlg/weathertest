package com.toprate.test.weather.api.controller;

import com.toprate.test.weather.api.form.WeatherCondition;
import com.toprate.test.weather.api.form.WeatherRequest;
import com.toprate.test.weather.service.WeatherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    WebClient webClient;

    @Autowired
    WeatherService weatherService;

    @GetMapping("/pull-data")
    ResponseEntity<Object> test() {

        return ResponseEntity.ok(weatherService.pullData());
    }

    @GetMapping("/get-information")
    ResponseEntity<Object> getColdestAndHotttest(WeatherRequest request) {
        WeatherCondition condition = new WeatherCondition();
        BeanUtils.copyProperties(request, condition);
        return ResponseEntity.ok(weatherService.getColdestAndHottest(condition));
    }
}
