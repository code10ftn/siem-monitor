package com.code10.security.controller;

import com.code10.security.controller.exception.AuthorizationException;
import com.code10.security.model.LogEvent;
import com.code10.security.model.LogItem;
import com.code10.security.model.LogQuery;
import com.code10.security.security.PermissionConstants;
import com.code10.security.service.LogService;
import com.code10.security.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;

@RestController
@RequestMapping("api/logs")
public class LogController {

    private final LogService logService;

    private final RuleService ruleService;

    @Autowired
    public LogController(LogService logService, RuleService ruleService) {
        this.logService = logService;
        this.ruleService = ruleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_GET_LOG + "')")
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(logService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_GET_LOG + "')")
    public ResponseEntity query(@RequestBody LogQuery logQuery, Pageable pageable) {
        return new ResponseEntity<>(logService.query(logQuery, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody LogItem logItem, HttpServletRequest request) {
        // This is endpoint should be accessible only by agents, so check if client-side authentication was achieved.
        // Also there's no need to check the certificate's validity (whether it's in the server's truststore),
        // SSL automatically does that if the client provided any certificate.
        final X509Certificate[] certificates = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if (certificates == null || certificates.length == 0) {
            throw new AuthorizationException();
        }

        logItem.setIpAddress(request.getRemoteAddr());

        logItem = logService.create(logItem);
        ruleService.updateSession(new LogEvent(logItem));

        return new ResponseEntity<>(logItem.getId(), HttpStatus.CREATED);
    }
}
