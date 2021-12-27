package com.activecharge;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;

@JsonTypeName(value = "request")
@Data
@Builder
public class Request {
    private String merchant_id;
    private String signature;
    private String order_id;
    private String order_desc;
    private String currency;
    private String amount;
}
