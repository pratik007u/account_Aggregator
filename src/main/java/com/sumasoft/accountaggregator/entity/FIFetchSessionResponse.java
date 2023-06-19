package com.sumasoft.accountaggregator.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.FIDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
@Document(value = "fi-fetch-session-response")
public class FIFetchSessionResponse {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("FI")
    private ArrayList<FIDto> fi;

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

    public ArrayList<FIDto> getFi() {
        return fi;
    }

    public void setFi(ArrayList<FIDto> fi) {
        this.fi = fi;
    }
}
