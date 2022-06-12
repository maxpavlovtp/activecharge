package com.km220.ewelink;

import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;


@Value
@Builder
@ToString
public class EwelinkParameters {

  @NonNull
  String region;
  @NonNull
  String email;
  @NonNull
  String password;
}
