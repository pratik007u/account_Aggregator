package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public class KeyMaterial {
    @NonNull
    String cryptoAlg;
    @NonNull
    String curve;
    @NonNull
    String params;
    @NonNull
    @JsonProperty("DHPublicKey")
    DHPublicKey dhPublicKey;

    @NonNull
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

    public DHPublicKey getDhPublicKey() {
        return dhPublicKey;
    }

    public void setDhPublicKey(DHPublicKey dhPublicKey) {
        this.dhPublicKey = dhPublicKey;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
