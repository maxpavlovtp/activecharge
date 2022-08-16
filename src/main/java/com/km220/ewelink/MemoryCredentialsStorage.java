package com.km220.ewelink;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class MemoryCredentialsStorage implements CredentialsStorage {

  private final Map<String, EwelinkCredentials> cache = new ConcurrentHashMap<>();

  private static final String ENTRY_KEY = "credentials-key";

  @Override
  public EwelinkCredentials get(final Supplier<EwelinkCredentials> credentialsSupplier) {
    EwelinkCredentials credentials = cache.get(ENTRY_KEY);

    if (credentials == null) {
      credentials = relogin(credentialsSupplier, credentials);
    }

    return credentials;
  }

  @Override
  public EwelinkCredentials relogin(final Supplier<EwelinkCredentials> credentialsSupplier,
      final EwelinkCredentials oldCredentials) {

    return cache.compute(ENTRY_KEY, (key, credentials) -> {

      if (Objects.equals(credentials, oldCredentials)) {
        return credentialsSupplier.get();
      }

      return credentials;
    });
  }

  @Override
  public EwelinkCredentials login(final Supplier<EwelinkCredentials> credentialsSupplier) {
    return cache.compute(ENTRY_KEY, (key, credentials) -> credentialsSupplier.get());
  }
}
