package com.code10.security.controller;

import com.code10.security.model.dto.ReportRequest;
import com.code10.security.service.ReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reports")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(value = "generate", produces = {MediaType.APPLICATION_PDF_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity generate(@RequestBody ReportRequest reportRequest) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=report.pdf");

        return new ResponseEntity<>(reportService.getReport(reportRequest.getStartDate(), reportRequest.getEndDate()), headers, HttpStatus.OK);
    }
}
