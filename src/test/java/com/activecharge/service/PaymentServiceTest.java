package com.activecharge.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void checkout() throws UnsupportedEncodingException {
        String response = paymentService.checkout();
        Assertions.assertThat(response).contains("failure");
    }
}