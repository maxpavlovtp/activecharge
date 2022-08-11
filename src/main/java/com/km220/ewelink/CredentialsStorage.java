package com.km220.ewelink;

import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface CredentialsStorage {

  EwelinkCredentials get(Supplier<EwelinkCredentials> credentialsSupplier);

  EwelinkCredentials refresh(Supplier<EwelinkCredentials> credentialsSupplier,
      EwelinkCredentials oldCredentials);

  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @ToString
  class EwelinkCredentials {

    @EqualsAndHashCode.Include
    private final String token;
    private final String apiKey;
  }
}
