package com.sumasoft.accountaggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.ConsentStatusDto;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mukund.ghanwat on 15 May, 2023
 */
@Document(value = "consent-handle")
public class ConsentHandleResponse {

    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("ConsentHandle")
    private String consentHandle;
    @JsonProperty("ConsentStatus")
    private ConsentStatusDto consentStatus;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getConsentHandle() {
        return consentHandle;
    }

    public void setConsentHandle(String consentHandle) {
        this.consentHandle = consentHandle;
    }

    public ConsentStatusDto getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ConsentStatusDto consentStatus) {
        this.consentStatus = consentStatus;
    }
}
