package com.km220.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.km220.BaseIT;
import com.km220.service.device.DeviceState;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
@Disabled
class ChargerDeviceControllerIT extends BaseIT {

  @LocalServerPort
  private int port;

  @Value("${deviceId}")
  private String deviceId;

  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeEach
  void beforeEach() throws InterruptedException {
    Thread.sleep(1000);
  }

  @Test
  void checkDeviceStatus() {
    ResponseEntity<ChargerResponse<DeviceState>> response = restTemplate.exchange(
        url("/getDeviceStatus"),
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
  void start() {
    ResponseEntity<ChargerResponse<Void>> response = restTemplate.exchange(
        url("/start"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  void startSecs() {
    ResponseEntity<ChargerResponse<Void>> response = restTemplate.exchange(
        url("/startSecs?secs=1"),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        });
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  private URI url(String resourcePath) {
    return URI.create("http://localhost:" + port + "/device" + resourcePath);
  }
}
