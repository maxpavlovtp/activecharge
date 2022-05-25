package com.km220.api;

import com.km220.ewelink.api.model.Status;
import com.km220.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
public class ChargerController {

  private final DeviceService deviceService;

  public ChargerController(final DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("/charging")
  public ApiResponse start() throws Exception {
    Status status = deviceService.on();
    return status.getError() > 0 ? ApiResponse.fail() : ApiResponse.success();
  }
}
