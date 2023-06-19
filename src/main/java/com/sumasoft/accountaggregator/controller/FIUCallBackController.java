package com.sumasoft.accountaggregator.controller;

import com.sumasoft.accountaggregator.service.FIUCallBackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
@RequestMapping("/")
@RestController
public class FIUCallBackController {
    private static final Logger logger = LogManager.getLogger(FIUCallBackController.class);

    @Autowired
    private FIUCallBackService fiuCallBackService;


    @PostMapping("Consent/Notification")
    public ResponseEntity<?> ConsentNotification(@RequestBody Map request, @RequestHeader(value = "x-jws-signature") String xjwsSignature, @RequestHeader(value = "aa_api_key") String token) {
        ResponseEntity<?> entity = null;
        try {
            logger.info("ConsentNotification aa_api_key- " + token);
            logger.info("ConsentNotification JWS Signature- " + xjwsSignature);
            entity = fiuCallBackService.conSentNotification(request, token, xjwsSignature);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    @PostMapping("FI/Notification")
    public ResponseEntity fINotification(@RequestBody Map request, @RequestHeader(value = "x-jws-signature") String xjwsSignature, @RequestHeader(value = "aa_api_key") String token) {
        ResponseEntity<?> entity = null;
        try {
            logger.info("FINotification aa_api_key- " + token);
            logger.info("FINotification JWS Signature- " + xjwsSignature);
            entity = fiuCallBackService.fINotification(request, token, xjwsSignature);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    @PostMapping("Account/link/Notification")
    public ResponseEntity<?> getAccountLinkNotification(@RequestBody Map request) {
        ResponseEntity entity = null;
        try {
            if (!request.isEmpty() && request != null) entity = fiuCallBackService.checkLinkNotification(request);
        } catch (Exception exception) {
            logger.error(exception.getMessage());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());

    }

}
