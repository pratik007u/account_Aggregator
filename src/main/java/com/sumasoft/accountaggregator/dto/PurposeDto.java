package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PurposeDto implements Serializable {

    private String code;
    private String refUri;
    private String text;
    @JsonProperty("Category")
    private CategoryDto category;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefUri() {
        return refUri;
    }

    public void setRefUri(String refUri) {
        this.refUri = refUri;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }
}
