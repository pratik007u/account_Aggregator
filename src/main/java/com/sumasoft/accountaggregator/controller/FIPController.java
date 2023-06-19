package com.sumasoft.accountaggregator.controller;

import com.sumasoft.accountaggregator.service.FIPService;
import com.sumasoft.accountaggregator.service.dla.DeleteAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
@RestController
@RequestMapping("/")
public class FIPController {
    private static final Logger logger = LogManager.getLogger(FIPController.class);
    @Autowired
    private FIPService fipService;

    @Autowired
    private DeleteAccountService deleteAccountService;

    @PostMapping("Accounts/discover")
    ResponseEntity<?> accountsDiscover(@RequestBody Map request) {
        ResponseEntity<?> entity = null;
        try {
            entity = fipService.accountDiscover(request);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    @PostMapping("Accounts/link")
    ResponseEntity<?> accountsLink(@RequestBody Map request) {
        ResponseEntity<?> entity = null;
        try {
            entity = fipService.accountsLink(request);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    @GetMapping("Accounts/link/fetch")
    ResponseEntity<?> fetchAccountDetails(@RequestBody Map request) {
        ResponseEntity<?> entity = null;
        try {
            entity = fipService.fetchAccountDetails(request);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }

    @DeleteMapping("Accounts/link")
    ResponseEntity<?> deleteLinkedAccount(@RequestBody Map request) {
        ResponseEntity<?> entity = null;
        try {
            entity = deleteAccountService.deleteLinkedAccount(request);
        } catch (Exception exception) {
            logger.error(exception);
            return new ResponseEntity(exception.getMessage(), entity.getStatusCode());
        }
        return new ResponseEntity<>(entity.getBody(), entity.getStatusCode());
    }


}