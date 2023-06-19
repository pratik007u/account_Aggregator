package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.AccountDiscoveryResponse;
import com.sumasoft.accountaggregator.entity.AccountLinkResponse;
import com.sumasoft.accountaggregator.entity.FetchAccountsLinkResponse;
import com.sumasoft.accountaggregator.repository.AccountDiscoveryResponseRepository;
import com.sumasoft.accountaggregator.repository.AccountLinkResponseRepository;
import com.sumasoft.accountaggregator.repository.FetchAccountsLinkResponseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
@Repository
public class FIPDaoImpl implements FIPDao {
    private static final Logger logger = LogManager.getLogger(FIPDaoImpl.class);
    @Autowired
    private AccountDiscoveryResponseRepository accountDiscoveryResponseRepository;

    @Autowired
    private AccountLinkResponseRepository accountLinkResponseRepository;

    @Autowired
    private FetchAccountsLinkResponseRepository fetchAccountsLinkResponseRepository;

    /**
     * @param accountDiscoveryResponse
     * @return
     */
    @Override
    public Boolean saveAccountDiscoverResponse(AccountDiscoveryResponse accountDiscoveryResponse) {
        Boolean result = false;
        try {
            AccountDiscoveryResponse response = accountDiscoveryResponseRepository.save(accountDiscoveryResponse);
            if (response != null) result = true;
        } catch (Exception exception) {
            logger.info(exception);
            result = false;
        }
        return result;
    }

    /**
     * @param accountLinkResponse
     * @return
     */
    @Override
    public Boolean saveLinkedAccounts(AccountLinkResponse accountLinkResponse) {
        Boolean result = false;
        try {
            AccountLinkResponse response = accountLinkResponseRepository.save(accountLinkResponse);
            if (response != null) result = true;
        } catch (Exception exception) {
            logger.info(exception);
            result = false;
        }
        return result;
    }

    /**
     * @param fetchAccountsLinkResponse
     * @return
     */
    @Override
    public Boolean saveAccountDetails(FetchAccountsLinkResponse fetchAccountsLinkResponse) {
        Boolean result = false;
        try {
            FetchAccountsLinkResponse response = fetchAccountsLinkResponseRepository.save(fetchAccountsLinkResponse);
            if (response != null)
                result = true;
        } catch (Exception exception) {
            logger.error(exception);
            result = false;
        }
        return result;
    }
}
