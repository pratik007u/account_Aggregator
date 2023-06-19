package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public class AccountDiscoverRequest implements Serializable {

    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("Customer")
    private FIPCustomerDto customer;
    @JsonProperty("FITypes")
    private List<String> fiTypes;

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

    public FIPCustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(FIPCustomerDto customer) {
        this.customer = customer;
    }

    public List<String> getFiTypes() {
        return fiTypes;
    }

    public void setFiTypes(List<String> fiTypes) {
        this.fiTypes = fiTypes;
    }
}
