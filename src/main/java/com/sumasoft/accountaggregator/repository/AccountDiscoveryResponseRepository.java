package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.AccountDiscoveryResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public interface AccountDiscoveryResponseRepository extends MongoRepository<AccountDiscoveryResponse, String> {
}
