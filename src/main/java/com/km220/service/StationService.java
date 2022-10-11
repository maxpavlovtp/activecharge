package com.km220.service;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.service.device.DeviceService;
import com.km220.service.device.DeviceState;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StationService {

	private final StationRepository stationRepository;
	private final DeviceService deviceService;

	public StationService(final StationRepository stationRepository, DeviceService deviceService) {
		this.stationRepository = stationRepository;
		this.deviceService = deviceService;
	}

	public List<StationEntity> getAllStations() {
		return stationRepository.findAll();
	}

	public boolean isOnline(String station) {
		String deviceId = stationRepository.getByNumber(station).getDeviceId();
		try {
			DeviceState state = deviceService.getState(deviceId);
			log.info("Got device state: {}", state);
			return true;
		} catch (Throwable t) {
			log.error("Device is not online: {}", t.getMessage());
			return false;
		}
	}
}
