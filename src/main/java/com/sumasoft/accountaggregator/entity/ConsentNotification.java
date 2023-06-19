package com.sumasoft.accountaggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.ConsentStatusNotificationDto;
import com.sumasoft.accountaggregator.dto.NotifierDto;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
@Document(value = "consent-notification")
public class ConsentNotification {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("Notifier")
    private NotifierDto notifier;
    @JsonProperty("ConsentStatusNotification")
    private ConsentStatusNotificationDto consentStatusNotification;

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

    public ConsentStatusNotificationDto getConsentStatusNotification() {
        return consentStatusNotification;
    }

    public void setConsentStatusNotification(ConsentStatusNotificationDto consentStatusNotification) {
        this.consentStatusNotification = consentStatusNotification;
    }
}
