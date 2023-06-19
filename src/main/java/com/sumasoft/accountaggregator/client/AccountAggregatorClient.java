package com.sumasoft.accountaggregator.client;

import com.sumasoft.accountaggregator.dto.AccountLinkNotificationDto;
import com.sumasoft.accountaggregator.dto.GenerateConsentRequest;
import org.springframework.http.ResponseEntity;

public interface AccountAggregatorClient {
    ResponseEntity<?> generateConsent(GenerateConsentRequest generateConsentRequest) throws Exception;

    ResponseEntity<?> checkConsentHandleStatus(String consentHandle) throws Exception;

    ResponseEntity<?> fetchConsent(String id) throws Exception;

    ResponseEntity<?> fetchSessionDataById(String sessionId) throws Exception;

    ResponseEntity getAccountLinkNotification(AccountLinkNotificationDto accountLinkNotificationDto) throws Exception;

    ResponseEntity fetchFiRequest(String consentDetailToSign);

    Boolean isValidSignature(String jwsSignature, String body);
}
