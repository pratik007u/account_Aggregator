package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 24 Apr, 2023
 */
public class AccountLinkStatusNotificationDto implements Serializable {
    private String accRefNumber;
    private String customerAddress;
    private String linkRefNumber;
    private String linkStatus;

    public String getAccRefNumber() {
        return accRefNumber;
    }

    public void setAccRefNumber(String accRefNumber) {
        this.accRefNumber = accRefNumber;
    }

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

    public String getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(String linkStatus) {
        this.linkStatus = linkStatus;
    }
}
