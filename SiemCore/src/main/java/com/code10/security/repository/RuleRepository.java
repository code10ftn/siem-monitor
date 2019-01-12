package com.code10.security.repository;

import com.code10.security.model.AlarmRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<AlarmRule, Long> {
}
