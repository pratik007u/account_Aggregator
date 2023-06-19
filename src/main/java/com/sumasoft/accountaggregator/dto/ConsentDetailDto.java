package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConsentDetailDto {

    private String consentStart;
    private String consentExpiry;
    private String consentMode;
    private String fetchType;
    @JsonProperty("DataConsumer")
    private DataConsumerDto dataConsumer;
    private List<String> fiTypes;
    private List<String> consentTypes;
    @JsonProperty("FIDataRange")
    private FIDataRangeDto fIDataRange;
    @JsonProperty("Frequency")
    private Frequency frequency;
    @JsonProperty("DataLife")
    private Frequency dataLife;
    @JsonProperty("DataFilter")
    private List<DataFilter> dataFilter;
    @JsonProperty("Customer")
    private CustomerDto customer;

    @JsonProperty("Purpose")
    private PurposeDto purpose;

    public String getConsentStart() {
        return consentStart;
    }

    public void setConsentStart(String consentStart) {
        this.consentStart = consentStart;
    }

    public String getConsentExpiry() {
        return consentExpiry;
    }

    public void setConsentExpiry(String consentExpiry) {
        this.consentExpiry = consentExpiry;
    }

    public String getConsentMode() {
        return consentMode;
    }

    public void setConsentMode(String consentMode) {
        this.consentMode = consentMode;
    }

    public String getFetchType() {
        return fetchType;
    }

    public void setFetchType(String fetchType) {
        this.fetchType = fetchType;
    }

    public DataConsumerDto getDataConsumer() {
        return dataConsumer;
    }

    public void setDataConsumer(DataConsumerDto dataConsumer) {
        this.dataConsumer = dataConsumer;
    }

    public List<String> getFiTypes() {
        return fiTypes;
    }

    public void setFiTypes(List<String> fiTypes) {
        this.fiTypes = fiTypes;
    }

    public List<String> getConsentTypes() {
        return consentTypes;
    }

    public void setConsentTypes(List<String> consentTypes) {
        this.consentTypes = consentTypes;
    }

    public FIDataRangeDto getfIDataRange() {
        return fIDataRange;
    }

    public void setfIDataRange(FIDataRangeDto fIDataRange) {
        this.fIDataRange = fIDataRange;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Frequency getDataLife() {
        return dataLife;
    }

    public void setDataLife(Frequency dataLife) {
        this.dataLife = dataLife;
    }

    public List<DataFilter> getDataFilter() {
        return dataFilter;
    }

    public void setDataFilter(List<DataFilter> dataFilter) {
        this.dataFilter = dataFilter;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public PurposeDto getPurpose() {
        return purpose;
    }

    public void setPurpose(PurposeDto purpose) {
        this.purpose = purpose;
    }
}
