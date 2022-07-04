package com.km220.ewelink.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.model.device.Device;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class DeviceV2 {

  private int error;
  private int msg;
  private Device data;
}
