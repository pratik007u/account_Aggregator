package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.FetchAccountsLinkResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public interface FetchAccountsLinkResponseRepository extends MongoRepository<FetchAccountsLinkResponse, String> {
}
