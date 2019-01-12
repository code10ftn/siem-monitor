package com.code10.security.repository;

import com.code10.security.model.LogItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogItem, String>, LogQueryRepository {

    Page<LogItem> findAllByOrderByTimestampDesc(Pageable pageable);
}
