package com.sumasoft.accountaggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.AccLinkDetailsDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
@Document(value = "fetch-accountslink-response")
public class FetchAccountsLinkResponse {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("AccLinkDetails")
    private ArrayList<AccLinkDetailsDto> accLinkDetails;

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

    public ArrayList<AccLinkDetailsDto> getAccLinkDetails() {
        return accLinkDetails;
    }

    public void setAccLinkDetails(ArrayList<AccLinkDetailsDto> accLinkDetails) {
        this.accLinkDetails = accLinkDetails;
    }
}
