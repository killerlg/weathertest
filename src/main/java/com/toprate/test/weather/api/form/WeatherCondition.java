package com.toprate.test.weather.api.form;

import com.toprate.test.weather.validator.DateString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeatherCondition {
    @DateString(format = "yyyy-MM-dd")
    String date;
}
