package com.code10.security.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Model for creating a new rule, parsed to a concrete Drools rule.
 */
@Entity
public class AlarmRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "rule_interval")
    private long interval;

    private int repetitions;

    @Enumerated(EnumType.STRING)
    private AlarmPriority priority;

    private String logSystem;

    private String logIpAddress;

    private String logHostName;

    private String logSourceName;

    private String logProcessId;

    private String logFacility;

    private String logSeverity;

    private String logMessage;

    private String logRaw;

    private boolean logIpAddressRepeated;

    private boolean logHostNameRepeated;

    private boolean logSourceNameRepeated;

    private boolean logProcessIdRepeated;

    private boolean logFacilityRepeated;

    private boolean logSeverityRepeated;

    private boolean logMessageRepeated;

    private boolean logRawRepeated;

    public AlarmRule() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public AlarmPriority getPriority() {
        return priority;
    }

    public void setPriority(AlarmPriority priority) {
        this.priority = priority;
    }

    public String getLogSystem() {
        return logSystem;
    }

    public void setLogSystem(String logSystem) {
        this.logSystem = logSystem;
    }

    public String getLogIpAddress() {
        return logIpAddress;
    }

    public void setLogIpAddress(String logIpAddress) {
        this.logIpAddress = logIpAddress;
    }

    public String getLogHostName() {
        return logHostName;
    }

    public void setLogHostName(String logHostName) {
        this.logHostName = logHostName;
    }

    public String getLogSourceName() {
        return logSourceName;
    }

    public void setLogSourceName(String logSourceName) {
        this.logSourceName = logSourceName;
    }

    public String getLogProcessId() {
        return logProcessId;
    }

    public void setLogProcessId(String logProcessId) {
        this.logProcessId = logProcessId;
    }

    public String getLogFacility() {
        return logFacility;
    }

    public void setLogFacility(String logFacility) {
        this.logFacility = logFacility;
    }

    public String getLogSeverity() {
        return logSeverity;
    }

    public void setLogSeverity(String logSeverity) {
        this.logSeverity = logSeverity;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogRaw() {
        return logRaw;
    }

    public void setLogRaw(String logRaw) {
        this.logRaw = logRaw;
    }

    public boolean isLogIpAddressRepeated() {
        return logIpAddressRepeated;
    }

    public void setLogIpAddressRepeated(boolean logIpAddressRepeated) {
        this.logIpAddressRepeated = logIpAddressRepeated;
    }

    public boolean isLogHostNameRepeated() {
        return logHostNameRepeated;
    }

    public void setLogHostNameRepeated(boolean logHostNameRepeated) {
        this.logHostNameRepeated = logHostNameRepeated;
    }

    public boolean isLogSourceNameRepeated() {
        return logSourceNameRepeated;
    }

    public void setLogSourceNameRepeated(boolean logSourceNameRepeated) {
        this.logSourceNameRepeated = logSourceNameRepeated;
    }

    public boolean isLogProcessIdRepeated() {
        return logProcessIdRepeated;
    }

    public void setLogProcessIdRepeated(boolean logProcessIdRepeated) {
        this.logProcessIdRepeated = logProcessIdRepeated;
    }

    public boolean isLogFacilityRepeated() {
        return logFacilityRepeated;
    }

    public void setLogFacilityRepeated(boolean logFacilityRepeated) {
        this.logFacilityRepeated = logFacilityRepeated;
    }

    public boolean isLogSeverityRepeated() {
        return logSeverityRepeated;
    }

    public void setLogSeverityRepeated(boolean logSeverityRepeated) {
        this.logSeverityRepeated = logSeverityRepeated;
    }

    public boolean isLogMessageRepeated() {
        return logMessageRepeated;
    }

    public void setLogMessageRepeated(boolean logMessageRepeated) {
        this.logMessageRepeated = logMessageRepeated;
    }

    public boolean isLogRawRepeated() {
        return logRawRepeated;
    }

    public void setLogRawRepeated(boolean logRawRepeated) {
        this.logRawRepeated = logRawRepeated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AlarmRule alarmRule = (AlarmRule) o;
        return id == alarmRule.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
