package com.km220.ewelink;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;

public class TokenStorageInMemoryImpl implements TokenStorage {

  private final Map<String, String> tokenCache = new ConcurrentHashMap<>();

  private static final String ENTRY_KEY = "token-key";

  @Override
  public String get(final Supplier<String> tokenExtractor) {
    String token =  tokenCache.get(ENTRY_KEY);
    if (StringUtils.isEmpty(token)) {
      token = refresh(tokenExtractor, token);
    }
    return token;
  }

  @Override
  public String refresh(final Supplier<String> tokenExtractor, final String oldToken) {
    return tokenCache.compute(ENTRY_KEY, (key, value) -> {
      if (Objects.equals(value, oldToken)) {
        return tokenExtractor.get();
      }
      return value;
    });
  }
}
