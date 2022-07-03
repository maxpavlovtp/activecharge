package com.km220.ewelink;

public abstract class AbstractEwelinkApiTest {

  protected final EwelinkClient ewelinkClient;

  protected static final String BOILER_DEVICE_ID = "1001323420";
  protected static final String STAGE_DEVICE_ID = "10013bb124";

  private static final String EMAIL = "maxpavlov.dp@gmail.com";
  private static final String PASSWORD = "Nopassword1";
  private static final String REGION = "eu";
  private static final String COUNTRY_CODE = "+380";
  private static final String APP_ID = "oeVkj2lYFGnJu5XUtWisfW4utiN4u9Mq";
  private static final String APP_SECRET = "6Nz4n0xA8s8qdxQf2GqurZj2Fs55FUvM";

  protected AbstractEwelinkApiTest() {
    ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD, COUNTRY_CODE))
        .applicationId(APP_ID)
        .applicationSecret(APP_SECRET)
        .build();
  }
}
