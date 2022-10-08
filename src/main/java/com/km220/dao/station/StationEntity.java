package com.km220.dao.station;

import com.km220.dao.DatabaseEntity;
import com.km220.dao.job.ChargingJobEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class StationEntity extends DatabaseEntity {

  public static final String NUMBER = "number";
  public static final String NAME = "name";
  public static final String PROVIDER_DEVICE_ID = "provider_device_id";

  public static final String COST_PER_HOUR = "cost_per_hour";

  private String name;
  private String number;
  private String deviceId;
  private String costPerHour;
	private ChargingJobEntity activeJob;
}
