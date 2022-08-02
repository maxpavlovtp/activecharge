package com.km220.aquaring.monobank;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InvoiceCreatorTest {

  @Test
  void makePost() throws IOException {
    assertThat(InvoiceCreator.generateCheckoutLink()).contains("pageUrl");
  }
}