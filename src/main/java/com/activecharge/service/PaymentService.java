package com.activecharge.service;

import com.activecharge.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
public class PaymentService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String checkout() throws UnsupportedEncodingException {
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

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "https://api.fondy.eu/api/checkout/url",
                Request.builder().merchant_id("1494220").build(),
                String.class);
        return responseEntity.getBody();

    }
}
