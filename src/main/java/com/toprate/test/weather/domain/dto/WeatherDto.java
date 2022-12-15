package com.toprate.test.weather.domain.dto;

import com.toprate.test.weather.domain.AscPickingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {

    private String date;

    private List<String> lowestTemperatureTime;

    private Float lowestTemperature;

    private List<String> highestTemperatureTime;

    private Float highestTemperature;
}
