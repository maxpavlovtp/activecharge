package com.km220.service;

import com.km220.dao.ewelink.EwelinkCredentialsEntity;
import com.km220.dao.ewelink.EwelinkCredentialsRepository;
import com.km220.ewelink.CredentialsStorage;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCredentialsStorage implements CredentialsStorage {

  private final EwelinkCredentialsRepository ewelinkCredentialsRepository;

  public DatabaseCredentialsStorage(final EwelinkCredentialsRepository ewelinkCredentialsRepository) {
    this.ewelinkCredentialsRepository = ewelinkCredentialsRepository;
  }

  @Override
  @Transactional
  public EwelinkCredentials get(final Supplier<EwelinkCredentials> credentialsSupplier) {
    EwelinkCredentialsEntity credentialsEntity = ewelinkCredentialsRepository.get(false);
    if (StringUtils.isEmpty(credentialsEntity.getToken())) {
     return refresh(credentialsSupplier, convert(credentialsEntity));
    }
    return convert(credentialsEntity);
  }

  @Override
  @Transactional
  public EwelinkCredentials refresh(final Supplier<EwelinkCredentials> credentialsSupplier,
      final EwelinkCredentials oldCredentials) {
    EwelinkCredentialsEntity credentialsEntity = ewelinkCredentialsRepository.get(true);
    if (Objects.equals(convert(credentialsEntity), oldCredentials)) {
      EwelinkCredentials newCredentials = credentialsSupplier.get();
      credentialsEntity.setToken(newCredentials.getToken());
      credentialsEntity.setApiKey(newCredentials.getApiKey());
      ewelinkCredentialsRepository.update(credentialsEntity);
    }
    return convert(credentialsEntity);
  }

  private static EwelinkCredentials convert(EwelinkCredentialsEntity credentialsEntity) {
    return new EwelinkCredentials(credentialsEntity.getToken(), credentialsEntity.getApiKey());
  }
}
