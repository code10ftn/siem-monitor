package com.code10.security.repository;

import com.code10.security.model.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Page<Alarm> findAllByOrderByTimestampDesc(Pageable pageable);

    Optional<Alarm> findByHashCode(int hashCode);

    @Query("select l.ipAddress, count(distinct a) from Alarm a join a.logs l where a.timestamp between :start and :end group by l.ipAddress ")
    List<Object[]> countAlarmByMachine(@Param("start") Date start, @Param("end") Date end);

    @Query("select l.system, count(distinct a) from Alarm a join a.logs l where a.timestamp between :start and :end group by l.system ")
    List<Object[]> countAlarmBySystem(@Param("start") Date start, @Param("end") Date end);
}
