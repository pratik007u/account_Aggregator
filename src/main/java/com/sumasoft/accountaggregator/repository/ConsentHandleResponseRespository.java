package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.ConsentHandleResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 15 May, 2023
 */
public interface ConsentHandleResponseRespository extends MongoRepository<ConsentHandleResponse, String> {
}
