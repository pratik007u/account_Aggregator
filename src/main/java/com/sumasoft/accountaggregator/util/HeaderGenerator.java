package com.sumasoft.accountaggregator.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * Created by mukund.ghanwat on 12 May, 2023
 */
@Component
public class HeaderGenerator {

    @Value("${com.accountaggregator.fipKey}")
    private String fipKey;

    public HttpEntity<String> generatePost(String consentDetailToSign, String signature) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        //headers.set("fip_api_key", fipKey);
        headers.set("x-jws-signature", signature);
        return new HttpEntity<String>(consentDetailToSign, headers);
    }

    public HttpEntity generateGet(String signature) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        //headers.set("fip_api_key", fipKey);
        headers.set("x-jws-signature", signature);
        return new HttpEntity<>(null, headers);
    }
}
