package com.km220.service;

import com.km220.dao.ewelink.EwelinkTokenEntity;
import com.km220.dao.ewelink.EwelinkTokenRepository;
import com.km220.ewelink.TokenStorage;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TokenStorageImpl implements TokenStorage {

  private final EwelinkTokenRepository ewelinkTokenRepository;

  public TokenStorageImpl(final EwelinkTokenRepository ewelinkTokenRepository) {
    this.ewelinkTokenRepository = ewelinkTokenRepository;
  }

  @Transactional
  @Override
  public String get(final Supplier<String> tokenExtractor) {
    EwelinkTokenEntity token = ewelinkTokenRepository.get(false);
    if (StringUtils.isEmpty(token.getToken())) {
      return refresh(tokenExtractor, token.getToken());
    }
    return token.getToken();
  }

  @Transactional
  @Override
  public String refresh(final Supplier<String> tokenExtractor, final String oldToken) {
    EwelinkTokenEntity token = ewelinkTokenRepository.get(true);
    if (Objects.equals(token.getToken(), oldToken)) {
      token.setToken(tokenExtractor.get());
      ewelinkTokenRepository.update(token);
    }
    return token.getToken();
  }
}
