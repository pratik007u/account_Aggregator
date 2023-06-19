package com.sumasoft.accountaggregator.repository;

import com.sumasoft.accountaggregator.entity.FINotification;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public interface FINotificationRepository extends MongoRepository<FINotification, String> {
}
