package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public class DataDto implements Serializable {
    private String linkRefNumber;
    private String maskedAccNumber;
    private String encryptedFI;

    public String getLinkRefNumber() {
        return linkRefNumber;
    }

    public void setLinkRefNumber(String linkRefNumber) {
        this.linkRefNumber = linkRefNumber;
    }

    public String getMaskedAccNumber() {
        return maskedAccNumber;
    }

    public void setMaskedAccNumber(String maskedAccNumber) {
        this.maskedAccNumber = maskedAccNumber;
    }

    public String getEncryptedFI() {
        return encryptedFI;
    }

    public void setEncryptedFI(String encryptedFI) {
        this.encryptedFI = encryptedFI;
    }
}
