package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public class AccountsDto implements Serializable {
    private String linkRefNumber;
    @JsonProperty("FIStatus")
    private String fIStatus;
    private String description;

    public String getLinkRefNumber() {
        return linkRefNumber;
    }

    public void setLinkRefNumber(String linkRefNumber) {
        this.linkRefNumber = linkRefNumber;
    }

    public String getfIStatus() {
        return fIStatus;
    }

    public void setfIStatus(String fIStatus) {
        this.fIStatus = fIStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
