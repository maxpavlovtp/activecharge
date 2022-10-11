package com.km220.service;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import com.km220.service.device.DeviceService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
		return deviceService.getState(stationRepository.getByNumber(station).getDeviceId())
				.getVoltage() > 0;
	}
}
