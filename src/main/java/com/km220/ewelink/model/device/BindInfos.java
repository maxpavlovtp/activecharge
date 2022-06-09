
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
public class BindInfos {

    @Default
    private List<String> alexa = new ArrayList<>();
    @Default
    private List<String> gaction = new ArrayList<>();
    @Default
    private Map<String, Object> additionalProperties = new HashMap<>();

}
