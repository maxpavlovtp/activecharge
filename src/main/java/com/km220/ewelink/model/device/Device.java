package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Device {

  private int error;
  private int msg;
  private Settings settings;
  private Family family;
  private String group;
  private Boolean online;
  @Default
  private List<Object> shareUsersInfo = new ArrayList<>();
  @Default
  private List<Object> groups = new ArrayList<>();
  @Default
  private List<Object> devGroups = new ArrayList<>();
  private String id;
  private String name;
  private String type;
  private String deviceid;
  private String apikey;
  private Extra extra;
  private Params params;
  private String createdAt;
  private Integer v;
  private String onlineTime;
  private String ip;
  private String location;
  private String offlineTime;
  private String deviceStatus;
  private Tags tags;
  private List<Object> sharedTo;
  private String devicekey;
  private String deviceUrl;
  private String brandName;
  private Boolean showBrand;
  private String brandLogoUrl;
  private String productModel;
  private DevConfig devConfig;
  private Integer uiid;

  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
