package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 24 Apr, 2023
 */
public class AccountLinkNotificationDto implements Serializable {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("Notifier")
    private NotifierDto notifier;
    @JsonProperty("AccountLinkStatusNotification")
    private AccountLinkStatusNotificationDto accountLinkStatusNotificationDto;

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

    public NotifierDto getNotifier() {
        return notifier;
    }

    public void setNotifier(NotifierDto notifier) {
        this.notifier = notifier;
    }

    public AccountLinkStatusNotificationDto getAccountLinkStatusNotificationDto() {
        return accountLinkStatusNotificationDto;
    }

    public void setAccountLinkStatusNotificationDto(AccountLinkStatusNotificationDto accountLinkStatusNotificationDto) {
        this.accountLinkStatusNotificationDto = accountLinkStatusNotificationDto;
    }
}
