package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public class FIPCustomerDto {

    private String id;
    @JsonProperty("Identifiers")
    private ArrayList<Identifiers> identifiers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Identifiers> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<Identifiers> identifiers) {
        this.identifiers = identifiers;
    }
}
