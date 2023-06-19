package com.sumasoft.accountaggregator.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public interface FIUCallBackService {
    ResponseEntity<?> conSentNotification(Map request, String token, String xjwsSignature);

    ResponseEntity<?> fINotification(Map request, String token, String xjwsSignature);

    ResponseEntity checkLinkNotification(Map request);
}
