package com.km220.controller;

import com.km220.config.StationScanProperties;
import com.km220.model.ChargingJob;
import com.km220.model.CreatedChargingJob;
import com.km220.service.ChargingService;
import com.km220.service.device.DeviceCache;
import com.km220.service.device.DeviceService;
import com.km220.service.device.DeviceState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class ChargerDeviceController {

  //TODO; refactor
  @Value("${device.chargeTimeSecs}")
  private int chargeTimeSecs;

  //TODO: refactor
  @Value("${deviceId}")
  private String deviceId;

  private final DeviceService deviceService;
  private final DeviceCache deviceCache;

  private final StationScanProperties stationScanProperties;

  //TODO: GET HTTP method should not change resource state.
  // todo remove after payment implementation
  @GetMapping("/start")
  public ChargerResponse<Void> start() {
    deviceService.toggleOn(deviceId, chargeTimeSecs);
    return new ChargerResponse<>("started");
  }

  // todo remove after payment implementation
  @GetMapping("/startSecs")
  public ChargerResponse<Void> startSecs(@RequestParam String secs) {
    deviceService.toggleOn(deviceId, Integer.parseInt(secs));
    return new ChargerResponse<>("startedSecs: " + secs);
  }

  @GetMapping("/getDeviceStatus")
  public ChargerResponse<DeviceState> getDeviceStatus() {
    return new ChargerResponse<>("getDeviceStatus", deviceCache.getDeviceStatus());
  }

  @GetMapping("/getChargingStatus")
  public ChargerResponse<Float> getChargingStatus() {
    return new ChargerResponse<>("chargedKwt", DeviceCache.chargedWt / 1000);
  }

  @GetMapping("/getChargingDurationLeftSecs")
  public ChargerResponse<Long> getChargeTimeLeftSecs() {
    return new ChargerResponse<>("getChargeTimeLeftSecs", 0L);
  }

  @GetMapping("/isPowerLimitOvelrloaded")
  public ChargerResponse<Boolean> isPowerLimitOvelrloaded() {
    return new ChargerResponse<>("isPowerLimitOvelrloaded", false);
  }

  @GetMapping("/getPowerLimit")
  public ChargerResponse<Integer> getPowerLimit() {
    return new ChargerResponse<>("getPowerLimit", 0);
  }

  @GetMapping("/isOverloadCheckCompleted")
  public ChargerResponse<Boolean> isOverloadCheckCompleted() {
    return new ChargerResponse<>("isOverloadCheckCompleted",
        !DeviceCache.isOn);
  }

  private final ChargingService chargingService;

  @Operation(summary = "Start charging")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created charging job",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = CreatedChargingJob.class))})
  })
  @PostMapping("/v2/start")
  public ResponseEntity<CreatedChargingJob> start(
      @Parameter(description = "Charge request parameters") @RequestBody ChargeRequest chargeRequest) {
    int chargePeriodInSeconds = chargeRequest.getChargePeriodInSeconds();

    UUID id = chargingService.start(chargeRequest.getStationNumber(),
        chargePeriodInSeconds);
    return ResponseEntity.status(HttpStatus.CREATED).body(CreatedChargingJob.builder()
        .id(id.toString())
        .scanIntervalMs(stationScanProperties.getScanIntervalMs())
        .build()
    );
  }

  @Operation(summary = "Get charging status by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Charging job status",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ChargingJob.class))})
  })
  @GetMapping("/v2/status")
  public ResponseEntity<ChargingJob> getStatus(
      @Parameter(description = "Charging job id") @NotBlank @RequestParam String id) {
    ChargingJob job = chargingService.get(id);
    return ResponseEntity.status(HttpStatus.OK).body(job);
  }

  @Operation(summary = "Get charging status by station number")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Charging job status",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = ChargingJob.class))})
  })
  @GetMapping("/v2/station/status")
  public ResponseEntity<ChargingJob> getStationStatus(
      @Parameter(description = "Station number") @NotBlank @RequestParam("station_number") String stationNumber) {
    ChargingJob job = chargingService.get(stationNumber);
    return ResponseEntity.status(HttpStatus.OK).body(job);
  }
}
