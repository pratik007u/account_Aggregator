package com.sumasoft.accountaggregator.service.dla;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public interface DeleteAccountService {
    ResponseEntity<?> deleteLinkedAccount(Map request);

}
