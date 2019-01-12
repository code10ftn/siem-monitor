package com.code10.security.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an alarm occurrence.
 */
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Date timestamp;

    /**
     * Rule for which the alarm was triggered.
     */
    @ManyToOne
    private AlarmRule rule;

    /**
     * Logs that triggered the alarm.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<LogItem> logs;

    private int hashCode;

    public Alarm() {
        this.timestamp = new Date();
    }

    public Alarm(AlarmRule rule, List<LogItem> logs) {
        this.timestamp = new Date();
        this.rule = rule;
        this.logs = logs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public AlarmRule getRule() {
        return rule;
    }

    public void setRule(AlarmRule rule) {
        this.rule = rule;
    }

    public List<LogItem> getLogs() {
        return logs;
    }

    public void setLogs(List<LogItem> logs) {
        this.logs = logs;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Alarm alarm = (Alarm) o;
        return Objects.equals(rule, alarm.rule) &&
                Objects.equals(logs, alarm.logs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rule, logs);
    }
}
