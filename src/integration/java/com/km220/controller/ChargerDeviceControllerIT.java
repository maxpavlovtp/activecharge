package com.km220.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.km220.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChargerDeviceControllerIT extends BaseIT {

  @LocalServerPort
  private int port;

  @Value("${deviceId}")
  private String deviceId;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void checkDeviceStatus() {
    ResponseEntity<ChargerResponse> response = restTemplate.getForEntity(
        url("/device/getDeviceStatus"),
        ChargerResponse.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  void checkPower() {
    ResponseEntity<ChargerResponse> response = restTemplate.getForEntity(
        url("/device/getPower"),
        ChargerResponse.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  private String url(String resourcePath) {
    return "http://localhost:" + port + "/" + resourcePath;
  }
}
