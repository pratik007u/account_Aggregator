package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.FetchConsentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public interface FetchConsentRepository extends MongoRepository<FetchConsentResponse, String> {
    public FetchConsentResponse findByConsentId(@Param("consentId") String consentId);
}
