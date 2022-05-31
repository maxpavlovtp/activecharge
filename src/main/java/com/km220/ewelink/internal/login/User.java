
package com.km220.ewelink.internal.login;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  private ClientInfo clientInfo;
  private String id;
  private String email;
  private String password;
  private String appId;
  private String apikey;
  private String createdAt;
  private Integer v;
  private String lang;
  private Boolean online;
  private String onlineTime;
  @Default
  private List<AppInfo> appInfos = new ArrayList<>();
  private String ip;
  private String location;
  private String offlineTime;
  private String userStatus;
  private BindInfos bindInfos;
  private String countryCode;
  private String currentFamilyId;
  private String language;
  private Extra extra;
  @Default
  private Map<String, Object> additionalProperties = new HashMap<>();
}
