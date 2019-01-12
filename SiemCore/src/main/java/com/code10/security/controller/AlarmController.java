package com.code10.security.controller;

import com.code10.security.security.PermissionConstants;
import com.code10.security.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_GET_ALARM + "')")
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(alarmService.findAll(pageable), HttpStatus.OK);
    }
}
