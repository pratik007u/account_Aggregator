package com.sumasoft.accountaggregator.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mukund.ghanwat on 15 May, 2023
 */
@Document(value = "fi-request-response")
public class FiRequestResponse {

    private String ver;
    private String timestamp;
    private String txnid;
    private String consentId;
    private String sessionId;

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

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
