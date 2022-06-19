package com.km220.ewelink.model.device;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SwitchState {
  ON("on"),
  OFF("off");

  private final String state;

  SwitchState(final String state) {
    this.state = state;
  }

  public boolean isOn() {
    return state.equals("on");
  }

  public boolean isOff() {
    return state.equals("off");
  }

  @JsonValue
  public String getState() {
    return state;
  }
}
