package com.code10.security.model.dto;

import java.util.Date;

public class ReportRequest {

    private Date startDate;

    private Date endDate;

    public ReportRequest() {
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
