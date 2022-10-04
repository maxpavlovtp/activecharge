package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Params {

  private BindInfos bindInfos;
  private String sledOnline;
  @JsonProperty("switch")
  private SwitchState switchState;
  private Integer uiActive;
  private String power;
  private String voltage;
  private String current;
  private String fwVersion;
  private String staMac;
  private Integer rssi;
  private Integer init;
  private String alarmType;
  @Default
  private List<Integer> alarmVValue = new ArrayList<>();
  @Default
  private List<Integer> alarmCValue = new ArrayList<>();
  @Default
  private List<Integer> alarmPValue = new ArrayList<>();
  private String oneKwh;
  private Integer timeZone;
  private Integer version;
  private String startup;
  private String pulse;
  private Integer pulseWidth;
  @Default
  private List<Timer> timers = new ArrayList<>();
  private String hundredDaysKwh;
  private OnlyDevice onlyDevice;
  private String ssid;
  private String bssid;
  private String endTime;
  private String startTime;

  private String subDevId;
  private String parentid;
  private String battery;
  private String trigTime;
  private String temperature;
  private String humidity;

  private Boolean online;

  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
