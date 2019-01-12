package com.code10.security.model.report;

public class Report {
    private String id;

    private long logCount;

    private long alarmCount;

    public Report() {
    }

    public Report(String id, long logCount, long alarmCount) {
        this.id = id;
        this.logCount = logCount;
        this.alarmCount = alarmCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getLogCount() {
        return logCount;
    }

    public void setLogCount(long logCount) {
        this.logCount = logCount;
    }

    public long getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(long alarmCount) {
        this.alarmCount = alarmCount;
    }
}
