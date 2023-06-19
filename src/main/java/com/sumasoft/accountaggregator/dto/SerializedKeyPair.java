package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import org.springframework.lang.Nullable;


public class SerializedKeyPair {
    @NonNull
    private String privateKey;
    @NonNull
    @JsonProperty("KeyMaterial")
    KeyMaterial keyMaterial;
    @Nullable
    ErrorInfo errorInfo;

    public String getPrivateKey() {
        return privateKey;
    }

    public KeyMaterial getKeyMaterial() {
        return keyMaterial;
    }

    public void setKeyMaterial(KeyMaterial keyMaterial) {
        this.keyMaterial = keyMaterial;
    }

    @Nullable
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(@Nullable ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}

