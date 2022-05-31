package com.km220.ewelink.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.km220.ewelink.EwelinkApiException;

public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private JsonUtils() {
  }

  public static String serialize(Object objectToSerialize) {
    try {
      return OBJECT_MAPPER.writeValueAsString(objectToSerialize);
    } catch (JsonProcessingException e) {
      throw new EwelinkApiException(e);
    }
  }
}
