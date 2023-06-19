package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.ConsentNotification;
import com.sumasoft.accountaggregator.entity.FINotification;
import com.sumasoft.accountaggregator.repository.ConsentNotificationRepository;
import com.sumasoft.accountaggregator.repository.FINotificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
@Repository
public class FIUCallBackDaoImpl implements FIUCallBackDao {
    private static final Logger logger = LogManager.getLogger(FIUCallBackDaoImpl.class);

    @Autowired
    private ConsentNotificationRepository consentNotificationRepository;

    @Autowired
    private FINotificationRepository fiNotificationRepository;


    /**
     * @param consentNotificationRequest
     * @return
     */
    @Override
    public Boolean saveConsentNotification(ConsentNotification consentNotificationRequest) {
        Boolean result = false;
        try {
            ConsentNotification consentNotification = consentNotificationRepository.save(consentNotificationRequest);
            if (consentNotification != null)
                result = true;
        } catch (Exception exception) {
            logger.error(exception);
        }
        return result;
    }

    /**
     * @param fiNotificationRequest
     * @return
     */
    @Override
    public Boolean savefINotification(FINotification fiNotificationRequest) {
        Boolean result = false;

        try {
            FINotification fiNotification = fiNotificationRepository.save(fiNotificationRequest);
            if (fiNotification != null)
                result = true;
        } catch (
                Exception exception) {
            logger.error(exception);
        }
        return result;
    }
}