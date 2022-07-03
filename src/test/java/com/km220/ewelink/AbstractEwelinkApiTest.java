package com.km220.ewelink;

import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractEwelinkApiTest {

  protected EwelinkClient ewelinkClient;

  protected static final String DRYER_DEVICE_ID = "1001323420";
  protected static final String BOILER_DEVICE_ID = "1000d61c41";

  private static final String EMAIL = "jasper.ua@gmail.com";
  private static final String PASSWORD = "12345678";
  private static final String REGION = "eu";
  private static final String COUNTRY_CODE = "+380";
  private static final String APP_ID = "YzfeftUVcZ6twZw1OoVKPRFYTrGEg01Q";
  private static final String APP_SECRET = "4G91qSoboqYO4Y0XJ0LPPKIsq8reHdfa";

  protected AbstractEwelinkApiTest() {
  }

  @BeforeEach
  void setup() {
    ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD, COUNTRY_CODE))
        .applicationId(APP_ID)
        .applicationSecret(APP_SECRET)
        .build();
  }
}
