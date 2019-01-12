package com.code10.security.repository.impl;

import com.code10.security.model.LogItem;
import com.code10.security.model.LogQuery;
import com.code10.security.model.report.Count;
import com.code10.security.repository.LogQueryRepository;
import com.code10.security.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public class LogQueryRepositoryImpl implements LogQueryRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public LogQueryRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<LogItem> query(LogQuery logQuery, Pageable pageable) {
        final Query query = new Query();

        query.with(pageable).with(new Sort(Sort.Direction.DESC, "timestamp"));

        if (logQuery.getTimestampStart() != null && logQuery.getTimestampEnd() != null) {
            query.addCriteria(Criteria.where(LogItem.TIMESTAMP_FIELD).gt(logQuery.getTimestampStart()).lt(logQuery.getTimestampEnd()));
        }

        if (!Util.nullOrEmpty(logQuery.getIpAddress())) {
            query.addCriteria(Criteria.where(LogItem.IP_ADDRESS_FIELD).regex(logQuery.getIpAddress()));
        }

        if (!Util.nullOrEmpty(logQuery.getHostName())) {
            query.addCriteria(Criteria.where(LogItem.HOST_NAME_FIELD).regex(logQuery.getHostName()));
        }

        if (!Util.nullOrEmpty(logQuery.getSourceName())) {
            query.addCriteria(Criteria.where(LogItem.SOURCE_NAME_FIELD).regex(logQuery.getSourceName()));
        }

        if (!Util.nullOrEmpty(logQuery.getProcessId())) {
            query.addCriteria(Criteria.where(LogItem.PROCESS_ID_FIELD).is(logQuery.getProcessId()));
        }

        if (!Util.nullOrEmpty(logQuery.getFacility())) {
            query.addCriteria(Criteria.where(LogItem.FACILITY_FIELD).regex(logQuery.getFacility()));
        }

        if (logQuery.getSeverity() != null) {
            query.addCriteria(Criteria.where(LogItem.SEVERITY_FIELD).is(logQuery.getSeverity()));
        }

        if (logQuery.getSystem() != null) {
            query.addCriteria(Criteria.where(LogItem.SYSTEM_FIELD).is(logQuery.getSystem()));
        }

        if (!Util.nullOrEmpty(logQuery.getMessage())) {
            query.addCriteria(TextCriteria.forDefaultLanguage().matchingAny(logQuery.getMessage()));
        }

        if (!Util.nullOrEmpty(logQuery.getRegex())) {
            query.addCriteria(Criteria.where(LogItem.RAW_FIELD).regex(logQuery.getRegex()));
        }

        final List<LogItem> logItems = mongoTemplate.find(query, LogItem.class);

        return PageableExecutionUtils.getPage(
                logItems,
                pageable,
                () -> mongoTemplate.count(query, LogItem.class));
    }

    @Override
    public List<Count> countByField(String field, Date startDate, Date endDate) {
        final MatchOperation filterDate = Aggregation.match(Criteria.where("timestamp").gte(startDate).lte(endDate));
        final GroupOperation groupByHost = Aggregation.group(field).count().as("count");
        final ProjectionOperation project = Aggregation.project().and("_id").as("key")
                .and("count").as("count");

        final Aggregation aggregation = newAggregation(filterDate, groupByHost, project);
        final AggregationResults<Count> result = mongoTemplate.aggregate(aggregation, LogItem.class, Count.class);

        return result.getMappedResults();
    }
}
