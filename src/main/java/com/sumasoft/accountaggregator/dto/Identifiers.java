package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public class Identifiers implements Serializable {
    private String category;
    private String type;
    private String value;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
