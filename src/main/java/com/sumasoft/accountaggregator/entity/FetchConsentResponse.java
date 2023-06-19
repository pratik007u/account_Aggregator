package com.sumasoft.accountaggregator.entity;

import com.sumasoft.accountaggregator.dto.ConsentUseDto;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
@Document(value = "fetch-consent-response")
public class FetchConsentResponse {
    private String ver;
    private String txnid;
    private String consentId;
    private String status;
    private String createTimestamp;
    private String signedConsent;
    private ConsentUseDto ConsentUse;

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

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(String createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getSignedConsent() {
        return signedConsent;
    }

    public void setSignedConsent(String signedConsent) {
        this.signedConsent = signedConsent;
    }

    public ConsentUseDto getConsentUse() {
        return ConsentUse;
    }

    public void setConsentUse(ConsentUseDto consentUse) {
        ConsentUse = consentUse;
    }
}
