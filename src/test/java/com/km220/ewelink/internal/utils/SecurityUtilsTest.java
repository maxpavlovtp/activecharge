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
    body.put("email", "maxpavlov.dp@gmail.com");

    String jsonBody = JsonUtils.serialize(body);

    String auth = SecurityUtils.makeAuthorizationSign("4G91qSoboqYO4Y0XJ0LPPKIsq8reHdfa", jsonBody);

    assertEquals("M9t3DIXCv3Jz+vKRnzVwiGb70oVQRxR5ZuTSM36o1Qs=", auth);
  }
}