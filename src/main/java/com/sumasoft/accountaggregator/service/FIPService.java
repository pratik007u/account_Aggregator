package com.sumasoft.accountaggregator.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public interface FIPService {
    ResponseEntity<?> accountDiscover(Map request);

    ResponseEntity<?> accountsLink(Map request);

    ResponseEntity<?> fetchAccountDetails(Map request);

}
