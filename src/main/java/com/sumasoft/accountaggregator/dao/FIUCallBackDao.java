package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.ConsentNotification;
import com.sumasoft.accountaggregator.entity.FINotification;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public interface FIUCallBackDao {
    Boolean saveConsentNotification(ConsentNotification consentNotification);

    Boolean savefINotification(FINotification fiNotification);
}
