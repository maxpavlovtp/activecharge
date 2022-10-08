package com.km220.controller;

import com.km220.service.OrderService;
import com.km220.service.job.ChargerService;
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
	private ChargerService chargerService;

	//  todo move to DB
	private static final HashMap<String, String> invoiceCache = new HashMap<>();

	@GetMapping("/generateCheckoutLink")
	public ResponseEntity<String> generateCheckoutLink(
			@NotBlank @RequestParam("station_number") String stationNumber,
			@NotBlank @RequestParam("hours") String hours) throws IOException {
		String monoResponse = orderService.generateCheckoutLink(stationNumber, Integer.valueOf(hours));
		return ResponseEntity.status(HttpStatus.OK).body(monoResponse);
	}

	@PostMapping("/callBackMono")
	public ResponseEntity<Void> callBackMono(@RequestBody String callBackMono) {
		orderService.processOrder(callBackMono);

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
