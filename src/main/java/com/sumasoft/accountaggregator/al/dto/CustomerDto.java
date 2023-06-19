package com.sumasoft.accountaggregator.al.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 08 Jun, 2023
 */
public class CustomerDto {
    private String id;
    @JsonProperty("Accounts")
    private ArrayList<AccountsDto> accounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<AccountsDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<AccountsDto> accounts) {
        this.accounts = accounts;
    }
}
