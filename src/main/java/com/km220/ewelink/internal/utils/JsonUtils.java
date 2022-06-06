package com.km220.ewelink.internal.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.km220.ewelink.EwelinkApiException;
import java.util.function.Function;

public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private JsonUtils() {
  }

  public static <T> String serialize(T objectToSerialize) {
    try {
      return OBJECT_MAPPER.writeValueAsString(objectToSerialize);
    } catch (JsonProcessingException e) {
      throw new EwelinkApiException(e);
    }
  }

  public static <T> T deserialize(String body, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(body, clazz);
    } catch (JsonProcessingException e) {
      throw new EwelinkApiException(e);
    }
  }

  public static <T> Function<JsonNode, T> jsonDataConverter(Class<T> clazz) {
    return jsonData -> {
      try {
        return OBJECT_MAPPER.treeToValue(jsonData, clazz);
      } catch (JsonProcessingException e) {
        throw new EwelinkApiException(e);
      }
    };
  }
}
