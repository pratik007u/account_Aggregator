package com.sumasoft.accountaggregator.accountslink;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public class DeleteLinkedAccountRequestDto implements Serializable {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("Account")
    private AccountDto account;

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

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }
}
