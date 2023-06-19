package com.sumasoft.accountaggregator.entity;

import com.sumasoft.accountaggregator.dto.ConsentDetailDto;

//@Document(value = "User_Account")
public class UserAccount {

    private String ver;
    private String timestamp;
    private String txnid;
    private ConsentDetailDto ConsentDetailDto;

    public ConsentDetailDto getConsentDetail() {
        return ConsentDetailDto;
    }

    public void setConsentDetail(ConsentDetailDto ConsentDetailDto) {
        this.ConsentDetailDto = ConsentDetailDto;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
