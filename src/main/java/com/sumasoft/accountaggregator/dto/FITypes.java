package com.sumasoft.accountaggregator.dto;

/**
 * Created by mukund.ghanwat on 02 Jun, 2023
 */
public enum FITypes {
    DEPOSIT, TERM_DEPOSIT("TERM-DEPOSIT"), RECURRING_DEPOSIT, SIP, CP, GOVT_SECURITIES, EQUITIES, BONDS, DEBENTURES, MUTUAL_FUNDS, ETF, IDR, CIS, AIF, INSURANCE_POLICIES, NPS, INVIT, REIT, OTHER;

    FITypes(String s) {
    }

    FITypes() {

    }
}

