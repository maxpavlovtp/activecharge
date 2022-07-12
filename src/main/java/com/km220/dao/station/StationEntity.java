package com.km220.dao.station;

import com.km220.dao.DatabaseEntity;
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

  String name;
  String number;
  String deviceId;

}
