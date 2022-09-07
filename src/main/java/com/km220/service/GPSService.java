package com.km220.service;

import static ca.rmen.sunrisesunset.SunriseSunset.getSunriseSunset;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GPSService {

  public boolean getUiNightMode() {
    Calendar[] sunriseSunset = getSunriseSunset(Calendar.getInstance(),
// todo: should be gps coordinates from table station
        50.433557, 30.617229);

    log.info("Sunrise for Kiev users at: " + sunriseSunset[0].getTime());
    log.info("Sunset for Kiev users at: " + sunriseSunset[1].getTime());

    LocalDate now = LocalDate.now();

    return now.isAfter(convertToLocalDateViaInstant(sunriseSunset[1].getTime()));
  }

  private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
        .atZone(ZoneOffset.ofHours(3))
        .toLocalDate();
  }
}
