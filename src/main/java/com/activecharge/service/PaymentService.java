package com.activecharge.service;

import com.activecharge.model.Request;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PaymentService {
    public String checkout() throws UnsupportedEncodingException {
        CloseableHttpClient httpclient = HttpClients.createDefault();

//        {
//            "hostname": "api.fondy.eu",
//                "port": 443,
//                "path": "/api/checkout/url/",
//                "method": "POST",
//                "headers": {
//            "User-Agent": "cloudipsp-nodejs-sdk",
//                    "Content-Type": "Content-type: application/json; charset=utf-8"
//        },
//            "body": "{\"request\":{\"order_id\":\"23\",\"order_desc\":\"test order\",\"currency\":\"UAH\",\"amount\":\"200\",\"merchant_id\":1494220,\"signature\":\"414821241f9c4fc4bb793adb6424b0bff7cfe1c2\"}}",
//                "timeout": 60000
//        }
        HttpPost httpPost = new HttpPost("https://api.fondy.eu/api/checkout/url/");
        httpPost.setHeader("User-Agent", "cloudipsp-nodejs-sdk");
        httpPost.setHeader("Content-Type", "application/json;charset=utf-8");


        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("request", Request.builder().amount("20").build().toString()));
        params.add(new BasicNameValuePair("timeout", "60000"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));


        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            InputStream is = response.getEntity().getContent();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String stringResponse = s.hasNext() ? s.next() : "";

            return stringResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
