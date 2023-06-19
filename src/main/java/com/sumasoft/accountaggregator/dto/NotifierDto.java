package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 20 Apr, 2023
 */
public class NotifierDto implements Serializable {
    private String type;
    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
