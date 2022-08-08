package com.km220.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {

  @Value("${monobank.token}")
  private String monoToken;

  @Value("${monobank.callBackHost}")
  private String callBackHost;


  public String generateCheckoutLink(String station) throws IOException {
    String result = null;

    URL url = new URL("https://api.monobank.ua/api/merchant/invoice/create");
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod("POST");
    http.setDoOutput(true);
    http.setRequestProperty("X-Token", monoToken);

    String body = "{\n"
        + "    \"amount\": 11000,\n"
        + "    \"ccy\": 980,\n"
        + "    \"merchantPaymInfo\": {\n"
        + "        \"reference\": \"84d0070ee4e44667b31371d8f8813947\",\n"
        + "        \"destination\": \"12 годин зарядки\",\n"
        + "        \"basketOrder\": []\n"
        + "    },\n"
        + "    \"redirectUrl\": \"http://" + callBackHost + "/charging?station=" + station + "\",\n"
        + "    \"webHookUrl\": \"http://" + callBackHost + "/order/callBackMono\",\n"
        + "    \"validity\": 3600,\n"
        + "    \"paymentType\": \"debit\"\n"
        + "}";

    byte[] out = body.getBytes(StandardCharsets.UTF_8);
    int length = out.length;

    http.setFixedLengthStreamingMode(length);
    http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    http.connect();
    try (OutputStream os = http.getOutputStream()) {
      os.write(out);
    }

    BufferedReader br = null;
    if (http.getResponseCode() == 200) {
      br = new BufferedReader(new InputStreamReader(http.getInputStream()));
      String strCurrentLine;
      while ((strCurrentLine = br.readLine()) != null) {
        log.info("Checkout info: {}", strCurrentLine);
        result = strCurrentLine;
      }
    } else {
      br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
      String strCurrentLine;
      while ((strCurrentLine = br.readLine()) != null) {
        log.error("Error: {}", strCurrentLine);
      }
    }

    return result;
  }
}
