package com.toprate.test.weather.domain.repository;

import com.toprate.test.weather.domain.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByTimeAndLocationId(Date time, Long locationId);

    @Query(value = "SELECT * FROM weather w1 " +
            "WHERE w1.temperature = " +
            "(SELECT MIN(w2.temperature) FROM weather w2 WHERE DATE_FORMAT(w2.time, '%Y-%m-%d') = :timeSearch) " +
            "AND DATE_FORMAT(w1.time, '%Y-%m-%d') = :timeSearch ", nativeQuery = true)
    List<Weather> getAllColdest(String timeSearch);

    @Query(value = "SELECT * FROM weather w1 " +
            "WHERE w1.temperature = " +
            "(SELECT MAX(w2.temperature) FROM weather w2 WHERE DATE_FORMAT(w2.time, '%Y-%m-%d') = :timeSearch) " +
            "AND DATE_FORMAT(w1.time, '%Y-%m-%d') = :timeSearch ", nativeQuery = true)
    List<Weather> getAllHottest(String timeSearch);
}
