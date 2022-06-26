package com.km220.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.km220.BaseIT;
import com.km220.model.DeviceStatus;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
    ResponseEntity<ChargerResponse<DeviceStatus>> response = restTemplate.exchange(
        url("/device/getDeviceStatus"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    assertEquals(deviceId, response.getBody().getData().getDeviceId());
  }

  @Test
  void checkPower() {
    ResponseEntity<ChargerResponse<Double>> response = restTemplate.exchange(
        url("/device/getPower"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getData());
    assertTrue(response.getBody().getData() >= 0.d);
  }

  private URI url(String resourcePath) {
    return URI.create("http://localhost:" + port + resourcePath);
  }
}
