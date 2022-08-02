package com.km220.controller;

import com.km220.aquaring.monobank.InvoiceCreator;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  @PostMapping("/generateCheckoutLink")
  public ResponseEntity<String> generateCheckoutLink(@RequestBody ChargeRequest chargeRequest)
      throws IOException {
    return ResponseEntity.status(HttpStatus.OK).body(InvoiceCreator.generateCheckoutLink());
  }

  @PostMapping("/callBackMono")
  public ResponseEntity<Void> callBackMono(@RequestBody String callBackMono) {
    log.info("Call back from monobank: {}", callBackMono);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
