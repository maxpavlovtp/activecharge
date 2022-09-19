package com.km220.controller;

import com.km220.config.StationScanProperties;
import com.km220.controller.converters.ChargingJobConverter;
import com.km220.controller.converters.StationStateConverter;
import com.km220.dao.job.ChargingJobEntity;
import com.km220.dao.job.ChargingJobState;
import com.km220.model.ChargingJob;
import com.km220.model.CreatedChargingJob;
import com.km220.model.StationState;
import com.km220.service.GPSService;
import com.km220.service.job.ChargerService;
import com.km220.service.job.ChargingJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

	private final StationScanProperties stationScanProperties;
	private final ChargerService chargerService;
	private final ChargingJobService chargingJobService;
	private final GPSService gpsService;

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

		UUID id = chargerService.start(chargeRequest.getStationNumber(),
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

		ChargingJobEntity job = chargingJobService.findByJobId(id);
		if (job == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(ChargingJobConverter.INSTANCE.apply(job));
	}

	@Operation(summary = "Get charging status by station number")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Charging job status",
					content = {@Content(mediaType = "application/json",
							schema = @Schema(implementation = ChargingJob.class))})
	})
	@GetMapping("/v2/station/status")
	public ResponseEntity<StationState> getStationStatus(
			@Parameter(description = "Station number") @NotBlank @RequestParam("station_number") String stationNumber) {

		ChargingJobEntity job = chargingJobService.findByStationNumber(stationNumber);
		StationState stationState = StationStateConverter.INSTANCE.apply(job);
		//TODO: refactor
		if (stationState.getLastJob() != null) {
			stationState.getLastJob().setUiNightMode(gpsService.getUiNightMode());
		}
		return ResponseEntity.status(HttpStatus.OK).body(stationState);
	}


	//todo remove
	@Autowired
	Environment env;

	@GetMapping("/v/station/statusAll")
	public ResponseEntity<List<ChargingJob>> getStatusAll() {

		String[] stations = Arrays.stream(env.getActiveProfiles()).toList().contains("220prod") ?
				new String[]{"1"} :
				new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

		// todo implemtent
		List<ChargingJob> jobs = chargingJobService.getInProgressJobs();

		jobs = Arrays.stream(stations)
				.map(id -> new ChargingJob(id, System.currentTimeMillis(), 3600))
				.map(job -> {
					if ("1".equals(job.getStationNumber())) {
						job.setState(ChargingJobState.DONE);
					} else {
						job.setState(ChargingJobState.IN_PROGRESS);
					}
					return job;
				})
				.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(jobs);
	}
}
