package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sumasoft.accountaggregator.dto.ConsentDto;
import com.sumasoft.accountaggregator.dto.FIDataRangeDto;
import com.sumasoft.accountaggregator.dto.KeyMaterialDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public class FIRequestDto {
    private String ver;
    private String timestamp;
    private String txnid;
    @JsonProperty("FIDataRange")
    private FIDataRangeDto fIDataRange;
    @JsonProperty("Consent")
    private ConsentDto consent;
    private KeyMaterialDto KeyMaterial;

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

    public FIDataRangeDto getfIDataRange() {
        return fIDataRange;
    }

    public void setfIDataRange(FIDataRangeDto fIDataRange) {
        this.fIDataRange = fIDataRange;
    }

    public ConsentDto getConsent() {
        return consent;
    }

    public void setConsent(ConsentDto consent) {
        this.consent = consent;
    }

    public KeyMaterialDto getKeyMaterial() {
        return KeyMaterial;
    }

    public void setKeyMaterial(KeyMaterialDto keyMaterial) {
        KeyMaterial = keyMaterial;
    }
}
