package com.km220.controller;

import com.km220.aquaring.monobank.InvoiceCreator;
import com.km220.model.ChargingJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

  @GetMapping("/generateCheckoutLink")
  public ResponseEntity<String> getStationStatus(
      @Parameter(description = "Station number") @NotBlank @RequestParam("station_number") String stationNumber)
      throws IOException {

    return ResponseEntity.status(HttpStatus.OK).body(InvoiceCreator.generateCheckoutLink());
  }

}
