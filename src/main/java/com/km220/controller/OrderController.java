package com.km220.controller;

import com.km220.service.OrderService;
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
	private OrderService orderService;

	@GetMapping("/generateCheckoutLink")
	public ResponseEntity<String> generateCheckoutLink(
			@NotBlank @RequestParam("station_number") String stationNumber,
			@NotBlank @RequestParam("hours") String hours) throws IOException {
		String checkoutLink = orderService.initOrder(stationNumber, Integer.valueOf(hours));
		return ResponseEntity.status(HttpStatus.OK).body(checkoutLink);
	}

	@PostMapping("/callBackMono")
	public ResponseEntity<String> callBackMono(@RequestBody String callBackMono) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.processOrder(callBackMono));
	}
}
