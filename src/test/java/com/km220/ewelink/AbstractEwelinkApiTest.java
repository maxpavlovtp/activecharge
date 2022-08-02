package com.km220.ewelink;

public abstract class AbstractEwelinkApiTest {

  protected final EwelinkClient ewelinkClient;

  protected static final String BOILER_DEVICE_ID = "100155422a";
  protected static final String STAGE_DEVICE_ID = "10013bb124";

  private static final String EMAIL = "maxpavlov.dp@gmail.com";
  private static final String PASSWORD = "Nopassword1";
  private static final String REGION = "eu";
  private static final String COUNTRY_CODE = "+380";
  private static final String APP_ID = "YzfeftUVcZ6twZw1OoVKPRFYTrGEg01Q";
  private static final String APP_SECRET = "4G91qSoboqYO4Y0XJ0LPPKIsq8reHdfa";

  protected AbstractEwelinkApiTest() {
    ewelinkClient = EwelinkClient.builder()
        .parameters(new EwelinkParameters(REGION, EMAIL, PASSWORD, COUNTRY_CODE))
        .applicationId(APP_ID)
        .applicationSecret(APP_SECRET)
        .tokenStorage(new TokenStorageInMemoryImpl())
        .build();
  }
}
