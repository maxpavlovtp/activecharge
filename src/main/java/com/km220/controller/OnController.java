package com.km220.controller;

import com.km220.service.ewelink.model.Status;
import com.km220.service.OnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/on")
public class OnController {

  @Autowired
  private OnService onService;


  @GetMapping("/start")
  public Response start() throws Exception {
    Status status = onService.on(8);
    return status.getError() > 0 ? Response.fail() : Response.success();
  }
}
