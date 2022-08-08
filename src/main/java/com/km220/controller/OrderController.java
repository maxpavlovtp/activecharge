package com.km220.controller;

import com.km220.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
  @Autowired
  OrderService orderService;

  @GetMapping("/generateCheckoutLink")
  public ResponseEntity<String> getStationStatus(
      @Parameter(description = "Station number") @NotBlank @RequestParam("station_number") String stationNumber)
      throws IOException {
    return ResponseEntity.status(HttpStatus.OK).body(orderService.generateCheckoutLink());
  }

  @PostMapping("/callBackMono")
  public ResponseEntity<Void> callBackMono(@RequestBody String callBackMono) {
    log.info("Call back from monobank: {}", callBackMono);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
