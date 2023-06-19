package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GenerateConsentRequest implements Serializable {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("ConsentDetail")
    private ConsentDetailDto consentDetail;

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

    public ConsentDetailDto getConsentDetail() {
        return consentDetail;
    }

    public void setConsentDetail(ConsentDetailDto consentDetail) {
        this.consentDetail = consentDetail;
    }
}
