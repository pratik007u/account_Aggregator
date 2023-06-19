package com.sumasoft.accountaggregator.dto;

import java.io.Serializable;

/**
 * Created by mukund.ghanwat on 21 Apr, 2023
 */
public class ConsentUseDto implements Serializable {
    private String logUri;
    private Integer count;
    private String lastUseDateTime;

    public String getLogUri() {
        return logUri;
    }

    public void setLogUri(String logUri) {
        this.logUri = logUri;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastUseDateTime() {
        return lastUseDateTime;
    }

    public void setLastUseDateTime(String lastUseDateTime) {
        this.lastUseDateTime = lastUseDateTime;
    }
}
