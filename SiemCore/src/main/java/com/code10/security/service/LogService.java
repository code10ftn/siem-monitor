package com.code10.security.service;

import com.code10.security.model.LogItem;
import com.code10.security.model.LogQuery;
import com.code10.security.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Page<LogItem> findAll(Pageable pageable) {
        return logRepository.findAllByOrderByTimestampDesc(pageable);
    }

    public Page<LogItem> query(LogQuery logQuery, Pageable pageable) {
        if (logQuery.getTimestampStart() != null && logQuery.getTimestampEnd() == null) {
            logQuery.setTimestampEnd(new Date());
        } else if (logQuery.getTimestampStart() == null && logQuery.getTimestampEnd() != null) {
            logQuery.setTimestampStart(new Date(Long.MIN_VALUE));
        }

        return logRepository.query(logQuery, pageable);
    }

    public LogItem create(LogItem logItem) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(logItem.getTimestamp());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getIpAddress());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getHostName());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getSourceName());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getProcessId());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getFacility());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getSeverity());
        stringBuilder.append(" ");
        stringBuilder.append(logItem.getMessage());
        stringBuilder.append(" ");

        logItem.setRaw(stringBuilder.toString());
        return logRepository.save(logItem);
    }
}
