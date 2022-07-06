package com.km220.ewelink.internal.utils;

import com.km220.ewelink.EwelinkClientException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class SecurityUtils {

  public static final String HMAC_SHA_256 = "HmacSHA256";

  private SecurityUtils() {
  }

  public static String generateNonce() {
    var secureRandom = new SecureRandom();
    var stringBuilder = new StringBuilder();
    for (var i = 0; i < 8; i++) {
      stringBuilder.append(secureRandom.nextInt(10));
    }
    return stringBuilder.toString();
  }

  public static String makeAuthorizationSign(String secret, String data) {
    try {
      byte[] byteKey = secret.getBytes(StandardCharsets.UTF_8);
      var sha256HMAC = Mac.getInstance(HMAC_SHA_256);
      var keySpec = new SecretKeySpec(byteKey, HMAC_SHA_256);
      sha256HMAC.init(keySpec);
      byte[] macData = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(macData);
    } catch (Exception e) {
      throw new EwelinkClientException(e);
    }
  }
}
