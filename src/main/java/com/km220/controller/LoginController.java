package com.km220.controller;

import java.io.IOException;
import java.util.Map;
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
  public ResponseEntity<String> ewelinkRedirect(@RequestParam Map<String, String> allParams) {
    log.info("oAuth login call back from ewelink is: {}", allParams.entrySet());

    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
