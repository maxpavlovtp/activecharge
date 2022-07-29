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
    ChargingJobEntity job = cache.get(id);

    logger.debug("Get entry from cache. key = {}, value = {}", id, job);

    return job;
  }

  @Override
  public ChargingJobEntity put(final String id, ChargingJobEntity job) {
    logger.debug("Put entry in cache. key = {}, value = {}", id, job);

    return cache.put(id, job);
  }
}
