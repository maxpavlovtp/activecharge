package com.km220.service;

import static ca.rmen.sunrisesunset.SunriseSunset.getSunriseSunset;

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

    Date sunset = sunriseSunset[1].getTime();
    log.trace("Sunset for Kiev users at: " + sunset);

    return new Date().getTime()> sunset.getTime();
  }
}
