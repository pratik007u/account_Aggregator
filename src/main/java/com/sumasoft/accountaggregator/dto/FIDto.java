package com.sumasoft.accountaggregator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public class FIDto implements Serializable {
    private String fipID;
    private ArrayList<DataDto> data;
    @JsonProperty("KeyMaterial")
    private KeyMaterialDto keyMaterial;

    public String getFipID() {
        return fipID;
    }

    public void setFipID(String fipID) {
        this.fipID = fipID;
    }

    public ArrayList<DataDto> getData() {
        return data;
    }

    public void setData(ArrayList<DataDto> data) {
        this.data = data;
    }

    public KeyMaterialDto getKeyMaterial() {
        return keyMaterial;
    }

    public void setKeyMaterial(KeyMaterialDto keyMaterial) {
        this.keyMaterial = keyMaterial;
    }
}
