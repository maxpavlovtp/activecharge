package com.km220.ewelink.model.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.km220.ewelink.model.device.Device;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class DeviceList {

  private List<Entry> thingList;
  private int total;

  @Jacksonized
  @Builder
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  @ToString
  public static class Entry {

    private int itemType;
    private Device itemData;
    private int index;
  }
}
