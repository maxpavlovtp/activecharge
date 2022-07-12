package com.km220.cache.impl;

import com.km220.cache.ChargingJobCache;
import com.km220.model.ChargingJob;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChargingJobInMemoryCache implements ChargingJobCache {

  private static final Map<UUID, ChargingJob> cache = new ConcurrentHashMap<>(100);

  private static final Logger logger = LoggerFactory.getLogger(ChargingJobInMemoryCache.class);

  @Override
  public ChargingJob get(final UUID id) {
    ChargingJob job = cache.get(id);

    logger.debug("Get from cache. key = {}, value = {}", id, job);

    return job;
  }

  @Override
  public ChargingJob put(final UUID id, ChargingJob job) {
    logger.debug("Put in cache. key = {}, value = {}", id, job);

    return cache.put(id, job);
  }
}
