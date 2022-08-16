package com.km220.ewelink.internal.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.km220.ewelink.EwelinkClientException;
import java.util.function.Function;

public final class JsonUtils {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    ;
  }

  private JsonUtils() {
  }

  public static <T> String serialize(T objectToSerialize) {
    try {
      return OBJECT_MAPPER.writeValueAsString(objectToSerialize);
    } catch (JsonProcessingException e) {
      throw new EwelinkClientException(e);
    }
  }

  public static <T> T deserialize(String body, Class<T> clazz) {
    try {
      return OBJECT_MAPPER.readValue(body, clazz);
    } catch (JsonProcessingException e) {
      throw new EwelinkClientException(e);
    }
  }

  public static <T> Function<JsonNode, T> jsonDataConverter(Class<T> clazz) {
    return jsonData -> {
      try {
        return OBJECT_MAPPER.treeToValue(jsonData, clazz);
      } catch (JsonProcessingException e) {
        throw new EwelinkClientException(e);
      }
    };
  }

  public static String addPropertyToJson(String json, String key, String value) {
    try {
      var jsonNode = OBJECT_MAPPER.readTree(json);
      ((ObjectNode) jsonNode).put(key, value);
      return OBJECT_MAPPER.writeValueAsString(jsonNode);
    } catch (JsonProcessingException e) {
      throw new EwelinkClientException(e);
    }
  }

  public static String prettyPrint(String json) {
    try {
      var jsonNode = OBJECT_MAPPER.readTree(json);
      return jsonNode.toPrettyString();
    } catch (JsonProcessingException e) {
      throw new EwelinkClientException(e);
    }
  }
}
