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
    body.put("password", "12345678");
    body.put("countryCode", "+380");
    body.put("email", "jasper.ua@gmail.com");

    String jsonBody = JsonUtils.serialize(body);

    String auth = SecurityUtils.makeAuthorizationSign("8SKQcsaGbsQMnhiLH3NKdLHNCBt2L8Xz", jsonBody);

    assertEquals("CDvHMUPigtv8RVX3t6Qlni2ur6riSomagUERmzL6zFw=", auth);
  }
}