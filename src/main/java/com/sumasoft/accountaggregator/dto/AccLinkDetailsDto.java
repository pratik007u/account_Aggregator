package com.sumasoft.accountaggregator.dto;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public class AccLinkDetailsDto {
    private String customerAddress;
    private String linkRefNumber;
    private String accRefNumber;
    private String status;

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

    public String getAccRefNumber() {
        return accRefNumber;
    }

    public void setAccRefNumber(String accRefNumber) {
        this.accRefNumber = accRefNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
