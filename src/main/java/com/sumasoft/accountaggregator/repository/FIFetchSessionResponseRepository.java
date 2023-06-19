package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.FIFetchSessionResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public interface FIFetchSessionResponseRepository extends MongoRepository<FIFetchSessionResponse, String> {
}
