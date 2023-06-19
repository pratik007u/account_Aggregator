package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public class KeyMaterialDto implements Serializable {
    private String cryptoAlg;
    private String curve;
    private String params;
    @JsonProperty("DHPublicKey")
    private DHPublicKeyDto dHPublicKey;
    @JsonProperty("Nonce")
    private String nonce;

    public String getCryptoAlg() {
        return cryptoAlg;
    }

    public void setCryptoAlg(String cryptoAlg) {
        this.cryptoAlg = cryptoAlg;
    }

    public String getCurve() {
        return curve;
    }

    public void setCurve(String curve) {
        this.curve = curve;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public DHPublicKeyDto getdHPublicKey() {
        return dHPublicKey;
    }

    public void setdHPublicKey(DHPublicKeyDto dHPublicKey) {
        this.dHPublicKey = dHPublicKey;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
