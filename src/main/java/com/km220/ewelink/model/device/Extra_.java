
package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Extra_ {

    private Integer uiid;
    private String description;
    private String brandId;
    private String apmac;
    private String mac;
    private String ui;
    private String modelInfo;
    private String model;
    private String manufacturer;
    private String staMac;
    private String chipid;
    @Default
    private Map<String, Object> additionalProperties = new HashMap<>();

}
