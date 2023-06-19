package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.FiRequestResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 15 May, 2023
 */
public interface FiRequestResponseRepository extends MongoRepository<FiRequestResponse, String> {
    FiRequestResponse findByConsentId(String consentId);

    FiRequestResponse findBySessionId(String sessionId);
}
