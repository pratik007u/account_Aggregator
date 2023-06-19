package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public class FIStatusResponseDto implements Serializable {
    private String fipID;
    private ArrayList<AccountsDto> Accounts;

    public String getFipID() {
        return fipID;
    }

    public void setFipID(String fipID) {
        this.fipID = fipID;
    }

    public ArrayList<AccountsDto> getAccounts() {
        return Accounts;
    }

    public void setAccounts(ArrayList<AccountsDto> accounts) {
        Accounts = accounts;
    }
}
