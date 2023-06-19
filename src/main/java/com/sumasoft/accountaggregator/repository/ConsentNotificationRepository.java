package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.ConsentNotification;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public interface ConsentNotificationRepository extends MongoRepository<ConsentNotification, String> {
}
