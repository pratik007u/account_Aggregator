package com.sumasoft.accountaggregator.al.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public class AccountsDto implements Serializable {
    @JsonProperty("FIType")
    private String fiType;
    private String accType;
    private String accRefNumber;
    private String maskedAccNumber;

    public String getFiType() {
        return fiType;
    }

    public void setFiType(String fiType) {
        this.fiType = fiType;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccRefNumber() {
        return accRefNumber;
    }

    public void setAccRefNumber(String accRefNumber) {
        this.accRefNumber = accRefNumber;
    }

    public String getMaskedAccNumber() {
        return maskedAccNumber;
    }

    public void setMaskedAccNumber(String maskedAccNumber) {
        this.maskedAccNumber = maskedAccNumber;
    }
}
