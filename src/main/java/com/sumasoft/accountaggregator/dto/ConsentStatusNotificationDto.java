package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public class ConsentStatusNotificationDto implements Serializable {
    private String consentId;
    private String consentHandle;
    private String consentStatus;

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getConsentHandle() {
        return consentHandle;
    }

    public void setConsentHandle(String consentHandle) {
        this.consentHandle = consentHandle;
    }

    public String getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(String consentStatus) {
        this.consentStatus = consentStatus;
    }
}
