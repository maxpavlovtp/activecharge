package com.km220.ewelink;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;


@Value
@AllArgsConstructor
@ToString
public class EwelinkParameters {

  @NonNull
  String region;
  @NonNull
  String email;
  @NonNull
  String password;
  @NonNull
  String countryCode;
}
