package com.code10.security.controller;

import com.code10.security.model.AlarmRule;
import com.code10.security.security.PermissionConstants;
import com.code10.security.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rules")
public class RuleController {

    private final RuleService ruleService;

    @Autowired
    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_POST_RULE + "')")
    public ResponseEntity create(@RequestBody AlarmRule rule) {
        return new ResponseEntity<>(ruleService.create(rule).getId(), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_GET_RULE + "')")
    public ResponseEntity findAll(Pageable pageable) {
        return new ResponseEntity<>(ruleService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_GET_RULE + "')")
    public ResponseEntity findById(@PathVariable long id) {
        return new ResponseEntity<>(ruleService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_PUT_RULE + "')")
    public ResponseEntity update(@RequestBody AlarmRule rule, @PathVariable long id) {
        ruleService.update(rule, id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + PermissionConstants.CAN_DELETE_RULE + "')")
    public ResponseEntity delete(@PathVariable long id) {
        ruleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
