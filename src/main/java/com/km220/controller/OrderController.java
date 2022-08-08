package com.km220.controller;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.service.OrderService;
import com.km220.service.device.DeviceService;
import com.km220.service.job.ChargingService;
import java.io.IOException;
import java.util.HashMap;
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
  private OrderService orderService;
  @Autowired
  private ChargingService chargingService;

  //  todo move to DB
  private static final HashMap<String, String> invoiceCache = new HashMap<>();

  @GetMapping("/generateCheckoutLink")
  public ResponseEntity<String> generateCheckoutLink(
      @NotBlank @RequestParam("station_number") String stationNumber) throws IOException {
    String monoResponse = orderService.generateCheckoutLink(stationNumber);
//    todo extract to service
    String invoiceId = fetchInvoiceId(monoResponse);
    invoiceCache.put(invoiceId, stationNumber);
    return ResponseEntity.status(HttpStatus.OK).body(monoResponse);
  }

  private String fetchInvoiceId(String monoResponse) {
    return monoResponse.replace("{\"invoiceId\":\"", "").split("\"")[0];
  }

  @PostMapping("/callBackMono")
  public ResponseEntity<Void> callBackMono(@RequestBody String callBackMono) {
    log.info("Call back from monobank: {}", callBackMono);

    String invoiceId = fetchInvoiceId(callBackMono);
    log.info("invoiceId: {}", invoiceId);
    String stationNumberFromCache = invoiceCache.get(invoiceId);
    log.info("stationNumberFromCache: {}", stationNumberFromCache);
    chargingService.start(stationNumberFromCache, 12 * 3600);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
