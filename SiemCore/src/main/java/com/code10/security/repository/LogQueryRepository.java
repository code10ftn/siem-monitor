package com.code10.security.repository;

import com.code10.security.model.LogItem;
import com.code10.security.model.LogQuery;
import com.code10.security.model.report.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface LogQueryRepository {

    Page<LogItem> query(LogQuery logQuery, Pageable pageable);

    List<Count> countByField(String field, Date startDate, Date endDate);
}
