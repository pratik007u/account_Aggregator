package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;


public class DHPublicKey {
    @NonNull
    String expiry;
    //Dont ask me why this is capital. I am just blindly following the spec ;)
    @NonNull
    @JsonProperty("Parameters")
    String parameters;
    @NonNull
    @JsonProperty("KeyValue")
    String keyValue;

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
