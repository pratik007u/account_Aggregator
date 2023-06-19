package com.sumasoft.accountaggregator.entity;

import com.sumasoft.accountaggregator.dto.FIStatusNotificationDto;
import com.sumasoft.accountaggregator.dto.NotifierDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
@Document(value = "fi-notification")
public class FINotification implements Serializable {
    private String ver;
    private String timestamp;
    private String txnid;
    private NotifierDto Notifier;
    private FIStatusNotificationDto FIStatusNotification;

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
        return Notifier;
    }

    public void setNotifier(NotifierDto notifier) {
        Notifier = notifier;
    }

    public FIStatusNotificationDto getFIStatusNotification() {
        return FIStatusNotification;
    }

    public void setFIStatusNotification(FIStatusNotificationDto FIStatusNotification) {
        this.FIStatusNotification = FIStatusNotification;
    }
}
