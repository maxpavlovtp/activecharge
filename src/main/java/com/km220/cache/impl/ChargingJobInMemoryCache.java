package com.km220.cache.impl;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChargingJobInMemoryCache implements ChargingJobCache {

  private static final Map<String, ChargingJobEntity> cache = new ConcurrentHashMap<>(100);

  private static final Logger logger = LoggerFactory.getLogger(ChargingJobInMemoryCache.class);

  @Override
  public ChargingJobEntity get(final String id) {
    return cache.get(id);
  }

  @Override
  public ChargingJobEntity put(final String id, ChargingJobEntity job) {
    return cache.put(id, job);
  }
}
