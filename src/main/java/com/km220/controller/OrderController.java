package com.km220.controller;

import com.km220.dao.station.StationRepository;
import com.km220.service.OrderService;
import com.km220.service.device.DeviceService;
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
  private DeviceService deviceService;

  @Autowired
  private StationRepository stationRepository;

  //  todo move to DB
  private static final HashMap<String, String> invoiceCache = new HashMap<>();

  @GetMapping("/generateCheckoutLink")
  public ResponseEntity<String> generateCheckoutLink(
      @NotBlank @RequestParam("station_number") String stationNumber) throws IOException {
    String monoResponse = orderService.generateCheckoutLink(stationNumber);
    String invoiceId = monoResponse.replace("{\"invoiceId\":\"", "").split("\"")[0];
    invoiceCache.put(invoiceId, monoResponse);
    return ResponseEntity.status(HttpStatus.OK).body(monoResponse);
  }

  @PostMapping("/callBackMono")
  public ResponseEntity<Void> callBackMono(@RequestBody String callBackMono) {
    log.info("Call back from monobank: {}", callBackMono);
    deviceService.toggleOn(stationRepository.getByNumber(invoiceCache.get("")).getDeviceId(),
        12 * 3600);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
