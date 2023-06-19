package com.sumasoft.accountaggregator.controller;

import com.sumasoft.accountaggregator.dto.GenerateConsentRequest;
import com.sumasoft.accountaggregator.service.AccountAggregatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class AccountAggregatorController {
    private static final Logger logger = LogManager.getLogger(AccountAggregatorController.class);
    @Autowired
    private AccountAggregatorService accountAggregatorService;

    //Consent Flow Consent Management APIs
    @PostMapping("generate-consent")
    public ResponseEntity<?> generateConsent(@RequestBody Map request) {
        GenerateConsentRequest generateConsentRequest = new GenerateConsentRequest();
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorService.generateConsent(request);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    //Consent Flow Consent Management APIs
    @GetMapping("check-consent-status/{consentHandle}")
    public ResponseEntity<?> checkConsentStatus(@PathVariable String consentHandle) {
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorService.checkConsentHandleStatus(consentHandle);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    //Consent Flow Consent Management APIs
    @GetMapping("fetch-consent/{id}")
    public ResponseEntity<?> fetchConsent(@PathVariable String id) {
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorService.fetchConsent(id);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    //Data Flow APIs for aggregation of FI
    @PostMapping("fi/request")
    public ResponseEntity<?> fetchFiRequest(@RequestBody Map request) {
        ResponseEntity<?> entity = null;
        try {
            entity = accountAggregatorService.fetchFiRequest(request);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    //Data Flow APIs for aggregation of FI
    @GetMapping(value = "FI/fetch/{sessionId}")
    public ResponseEntity<?> fetchSessionDataById(@PathVariable String sessionId) {
        ResponseEntity entity = null;
        try {
            entity = accountAggregatorService.fetchSessionDataById(sessionId);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity, entity.getStatusCode());
    }
}
