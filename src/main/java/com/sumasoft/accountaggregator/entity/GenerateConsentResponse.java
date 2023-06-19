package com.sumasoft.accountaggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.CustomerDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
@Document(value = "generate-consent-response")
public class GenerateConsentResponse implements Serializable {

    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("Customer")
    private CustomerDto customer;
    @JsonProperty("ConsentHandle")
    private String consentHandle;

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

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public String getConsentHandle() {
        return consentHandle;
    }

    public void setConsentHandle(String consentHandle) {
        this.consentHandle = consentHandle;
    }


}
