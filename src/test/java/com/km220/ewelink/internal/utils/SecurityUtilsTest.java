package com.km220.ewelink.internal.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class SecurityUtilsTest {

  @Test
  void generateNonce() {
    String nonce = SecurityUtils.generateNonce();

    assertTrue(nonce.matches("[a-zA-Z0-9]{8}"));
  }

  @Test
  void makeAuthorizationSign() {
    Map<String, String> body = new LinkedHashMap<>();
    body.put("password", "Nopassword1");
    body.put("countryCode", "+380");
    body.put("email", "eskimorollerr@gmail.com");

    String jsonBody = JsonUtils.serialize(body);

    String auth = SecurityUtils.makeAuthorizationSign("4G91qSoboqYO4Y0XJ0LPPKIsq8reHdfa", jsonBody);

    assertEquals("6watR8F3wNh9yPOOAzC0clRhba/ECl7TqCpJ5o6Z46o=", auth);
  }
}