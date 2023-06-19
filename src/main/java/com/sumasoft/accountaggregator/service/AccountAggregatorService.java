package com.sumasoft.accountaggregator.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AccountAggregatorService {
    ResponseEntity<?> generateConsent(Map request) throws Exception;

    ResponseEntity<?> checkConsentHandleStatus(String consentHandle) throws Exception;

    ResponseEntity<?> fetchConsent(String id) throws Exception;

    ResponseEntity<?> fetchFiRequest(Map request);

    ResponseEntity fetchSessionDataById(String sessionId);

    String generateNance();
}
