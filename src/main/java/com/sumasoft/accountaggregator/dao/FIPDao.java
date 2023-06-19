package com.sumasoft.accountaggregator.dao;

import com.sumasoft.accountaggregator.entity.AccountDiscoveryResponse;
import com.sumasoft.accountaggregator.entity.AccountLinkResponse;
import com.sumasoft.accountaggregator.entity.FetchAccountsLinkResponse;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public interface FIPDao {
    Boolean saveAccountDiscoverResponse(AccountDiscoveryResponse accountDiscoveryResponse);

    Boolean saveLinkedAccounts(AccountLinkResponse accountLinkResponse);

    Boolean saveAccountDetails(FetchAccountsLinkResponse fetchAccountsLinkResponse);
}
