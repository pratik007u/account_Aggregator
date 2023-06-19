package com.sumasoft.accountaggregator.client;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public interface FIPClient {
    ResponseEntity<?> accountDiscover(JSONObject jsonObject);

    ResponseEntity<?> accountsLink(JSONObject jsonObject);

    ResponseEntity<?> fetchAccountDetails(Map request);

    ResponseEntity<?> deleteLinkedAccount(JSONObject jsonObject);
}
