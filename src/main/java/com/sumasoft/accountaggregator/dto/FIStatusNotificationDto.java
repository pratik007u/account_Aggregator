package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public class FIStatusNotificationDto implements Serializable {
    private String sessionId;
    private String sessionStatus;
    private ArrayList<FIStatusResponseDto> FIStatusResponse;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public ArrayList<FIStatusResponseDto> getFIStatusResponse() {
        return FIStatusResponse;
    }

    public void setFIStatusResponse(ArrayList<FIStatusResponseDto> FIStatusResponse) {
        this.FIStatusResponse = FIStatusResponse;
    }
}
