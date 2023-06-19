package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.GenerateConsentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public interface GenerateConsentResponseRepository extends MongoRepository<GenerateConsentResponse, String> {
    GenerateConsentResponse findByConsentHandle(String consentHandle);
}
