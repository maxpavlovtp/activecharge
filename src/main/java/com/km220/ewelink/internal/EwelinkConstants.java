package com.km220.ewelink.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class EwelinkConstants {

  private EwelinkConstants() {
  }

  public static final String QUERY_ACTION = "query";
  public static final String UPDATE_ACTION = "update";

  public static final String START_CONSUMPTION = "start";
  public static final String STOP_CONSUMPTION = "stop";
  public static final String GET_CONSUMPTION = "get";
  public static final DateFormat CONSUMPTION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  static {
    CONSUMPTION_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  public static final String APP_USER_AGENT = "app";

  public static final int DEVICE_TYPE = 1;
  public static final int GROUP_TYPE = 1;
}
