package com.code10.security.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Document(collection = "logs")
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class LogItem {

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static final String IP_ADDRESS_FIELD = "ip_address";

    public static final String HOST_NAME_FIELD = "host_name";

    public static final String SOURCE_NAME_FIELD = "source_name";

    public static final String PROCESS_ID_FIELD = "process_id";

    public static final String FACILITY_FIELD = "facility";

    public static final String SEVERITY_FIELD = "severity";

    public static final String SYSTEM_FIELD = "system";

    public static final String MESSAGE_FIELD = "message";

    public static final String RAW_FIELD = "raw";

    private static final int MESSAGE_MAX_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long sqlId;

    @org.springframework.data.annotation.Id
    private String id;

    @Indexed(direction = IndexDirection.DESCENDING)
    @Field(TIMESTAMP_FIELD)
    private Date timestamp;

    @Field(IP_ADDRESS_FIELD)
    private String ipAddress;

    @Field(HOST_NAME_FIELD)
    private String hostName;

    @Field(SOURCE_NAME_FIELD)
    private String sourceName;

    @Field(PROCESS_ID_FIELD)
    private String processId;

    @Field(FACILITY_FIELD)
    private String facility;

    @Indexed
    @Field(SEVERITY_FIELD)
    private LogSeverity severity;

    @Field(SYSTEM_FIELD)
    @Column(name = "system_name")
    private String system;

    @TextIndexed
    @Field(MESSAGE_FIELD)
    @Column(length = MESSAGE_MAX_LENGTH)
    private String message;

    @Field(RAW_FIELD)
    @Column(length = MESSAGE_MAX_LENGTH)
    private String raw;

    public LogItem() {
    }

    public long getSqlId() {
        return sqlId;
    }

    public void setSqlId(long sqlId) {
        this.sqlId = sqlId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @JsonProperty(TIMESTAMP_FIELD)
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    @JsonProperty(IP_ADDRESS_FIELD)
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @JsonProperty("hostName")
    public String getHostName() {
        return hostName;
    }

    @JsonProperty(HOST_NAME_FIELD)
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @JsonProperty("sourceName")
    public String getSourceName() {
        return sourceName;
    }

    @JsonProperty(SOURCE_NAME_FIELD)
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @JsonProperty("processId")
    public String getProcessId() {
        return processId;
    }

    @JsonProperty(PROCESS_ID_FIELD)
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getFacility() {
        return facility;
    }

    @JsonProperty(FACILITY_FIELD)
    public void setFacility(String facility) {
        this.facility = facility;
    }

    public LogSeverity getSeverity() {
        return severity;
    }

    @JsonProperty(SEVERITY_FIELD)
    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getMessage() {
        return message;
    }

    @JsonProperty(MESSAGE_FIELD)
    public void setMessage(String message) {
        if (message.length() > MESSAGE_MAX_LENGTH) {
            message = message.substring(0, MESSAGE_MAX_LENGTH);
        }
        this.message = message;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        if (raw.length() > MESSAGE_MAX_LENGTH) {
            raw = raw.substring(0, MESSAGE_MAX_LENGTH);
        }
        this.raw = raw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LogItem logItem = (LogItem) o;
        return id.equals(logItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
