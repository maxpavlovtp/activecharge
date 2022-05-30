
package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tags {

    private String mAc4bBret;
    @Default
    private List<DisableTimer> disableTimers = new ArrayList<>();
    @Default
    private Map<String, Object> additionalProperties = new HashMap<>();

}
