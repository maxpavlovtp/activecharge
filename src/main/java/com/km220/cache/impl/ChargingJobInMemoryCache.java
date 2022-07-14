package com.km220.cache.impl;

import com.km220.cache.ChargingJobCache;
import com.km220.dao.job.ChargingJobEntity;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChargingJobInMemoryCache implements ChargingJobCache {

  private static final Map<UUID, ChargingJobEntity> cache = new ConcurrentHashMap<>(100);

  private static final Logger logger = LoggerFactory.getLogger(ChargingJobInMemoryCache.class);

  @Override
  public ChargingJobEntity get(final UUID id) {
    ChargingJobEntity job = cache.get(id);

    logger.debug("Get from cache. key = {}, value = {}", id, job);

    return job;
  }

  @Override
  public ChargingJobEntity put(final UUID id, ChargingJobEntity job) {
    logger.debug("Put in cache. key = {}, value = {}", id, job);

    return cache.put(id, job);
  }
}
