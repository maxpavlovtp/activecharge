package com.km220.controller;

import java.io.IOException;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  @GetMapping("/ewelinkRedirect")
  public ResponseEntity<String> generateCheckoutLink(
      @NotBlank @RequestParam("code") String code,
      @NotBlank @RequestParam("region") String region,
      @NotBlank @RequestParam("state") String state
  ) {
    log.info("code: {}, region: {}, state: {}", code, region, state);

    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
