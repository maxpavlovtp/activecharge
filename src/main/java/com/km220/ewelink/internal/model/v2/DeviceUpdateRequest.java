package com.km220.ewelink.internal.model.v2;

import com.km220.ewelink.model.device.Params;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DeviceUpdateRequest {

  int type;
  String id;
  Params params;
}
