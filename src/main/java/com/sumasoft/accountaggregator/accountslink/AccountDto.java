package com.sumasoft.accountaggregator.accountslink;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public class AccountDto implements Serializable {
    private String customerAddress;
    private String linkRefNumber;

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getLinkRefNumber() {
        return linkRefNumber;
    }

    public void setLinkRefNumber(String linkRefNumber) {
        this.linkRefNumber = linkRefNumber;
    }
}
