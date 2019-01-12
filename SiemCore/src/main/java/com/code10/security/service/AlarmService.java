package com.code10.security.service;

import com.code10.security.model.Alarm;
import com.code10.security.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;

    @Autowired
    public AlarmService(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    public Alarm create(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public Page<Alarm> findAll(Pageable pageable) {
        return alarmRepository.findAllByOrderByTimestampDesc(pageable);
    }

    public Optional<Alarm> findByHashCode(int hashCode) {
        return alarmRepository.findByHashCode(hashCode);
    }
}
