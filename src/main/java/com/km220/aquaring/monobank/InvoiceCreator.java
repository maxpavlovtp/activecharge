package com.km220.aquaring.monobank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class InvoiceCreator {

  public static String generateCheckoutLink() throws IOException {
    String result = null;

    URL url = new URL("https://api.monobank.ua/api/merchant/invoice/create");
    URLConnection con = url.openConnection();
    HttpURLConnection http = (HttpURLConnection) con;
    http.setRequestMethod("POST"); // PUT is another valid option
    http.setDoOutput(true);
    http.setRequestProperty("X-Token", "ux9Fn-4ob62pBQtM0O9z_gOKYhSo9SYBHk3g-2Bivw70");

    String body = "{\n"
        + "    \"amount\": 22000,\n"
        + "    \"ccy\": 980,\n"
        + "    \"merchantPaymInfo\": {\n"
        + "        \"reference\": \"84d0070ee4e44667b31371d8f8813947\",\n"
        + "        \"destination\": \"Покупка щастя\",\n"
        + "        \"basketOrder\": []\n"
        + "    },\n"
        + "    \"redirectUrl\": \"https://example.com/your/website/result/page\",\n"
        + "    \"webHookUrl\": \"https://example.com/mono/acquiring/webhook/maybesomegibberishuniquestringbutnotnecessarily\",\n"
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
        System.out.println(strCurrentLine);
        result = strCurrentLine;
      }
    } else {
      br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
      String strCurrentLine;
      while ((strCurrentLine = br.readLine()) != null) {
        System.out.println(strCurrentLine);
      }
    }

    return result;
  }
}
