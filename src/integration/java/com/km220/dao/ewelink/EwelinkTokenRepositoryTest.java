package com.km220.dao.ewelink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJdbcTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
@ActiveProfiles("integration-test")
class EwelinkTokenRepositoryTest {

  @Autowired
  private EwelinkTokenRepository ewelinkTokenRepository;

  @Sql("/dao/ewelink/init_token_table.sql")
  @Test
  void getToken_readOnly_shouldReturnTokenFromDb() {
    EwelinkTokenEntity ewelinkToken = ewelinkTokenRepository.get(false);

    assertNotNull(ewelinkToken);
    assertEquals("TOKEN_VAL", ewelinkToken.getToken());
  }

  @Test
  void getToken_readOnly_shouldReturnEmptyTokenFromDb() {
    EwelinkTokenEntity ewelinkToken = ewelinkTokenRepository.get(false);

    assertNotNull(ewelinkToken);
    assertNull(ewelinkToken.getToken());
  }

  @Test
  void updateToken_shouldUpdateTokenInDb() {
    EwelinkTokenEntity ewelinkToken = ewelinkTokenRepository.get(false);
    ewelinkToken.setToken("TOKEN_UPDATED_VAL");

    ewelinkTokenRepository.update(ewelinkToken);

    ewelinkToken = ewelinkTokenRepository.get(false);

    assertNotNull(ewelinkToken);
    assertEquals("TOKEN_UPDATED_VAL", ewelinkToken.getToken());
  }

  @Test
  void exclusivelyUpdateToken_shouldUpdateTokenInDb() {
    EwelinkTokenEntity ewelinkToken = ewelinkTokenRepository.get(true);
    ewelinkToken.setToken("TOKEN_UPDATED_VAL");

    ewelinkTokenRepository.update(ewelinkToken);

    ewelinkToken = ewelinkTokenRepository.get(true);

    assertNotNull(ewelinkToken);
    assertEquals("TOKEN_UPDATED_VAL", ewelinkToken.getToken());
  }
}
