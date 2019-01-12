package com.code10.security.model;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

/**
 * Added to the running kie session every time a new {@link LogItem} is created.
 * Used only by the rule engine, so that old events are automatically retracted from the session.
 */
@Role(Role.Type.EVENT)
@Expires("24h")
public class LogEvent {

    private LogItem logItem;

    public LogEvent() {
    }

    public LogEvent(LogItem logItem) {
        this.logItem = logItem;
    }

    public LogItem getLogItem() {
        return logItem;
    }

    public void setLogItem(LogItem logItem) {
        this.logItem = logItem;
    }
}
