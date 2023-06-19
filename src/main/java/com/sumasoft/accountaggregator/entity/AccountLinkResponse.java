package com.sumasoft.accountaggregator.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
@Document(value = "account-link")
public class AccountLinkResponse {
    private String ver;
    private String timestamp;
    private String txnid;
    private String AuthenticatorType;
    private String RefNumber;

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

    public String getAuthenticatorType() {
        return AuthenticatorType;
    }

    public void setAuthenticatorType(String authenticatorType) {
        AuthenticatorType = authenticatorType;
    }

    public String getRefNumber() {
        return RefNumber;
    }

    public void setRefNumber(String refNumber) {
        RefNumber = refNumber;
    }
}
