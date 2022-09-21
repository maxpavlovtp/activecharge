package com.km220.service;

import com.km220.dao.station.StationEntity;
import com.km220.dao.station.StationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StationService {

  private final StationRepository stationRepository;

  public StationService(final StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  public List<StationEntity> getAllStations() {
    return stationRepository.findAll();
  }
}
