package com.code10.security.service;

import com.code10.security.controller.exception.BadRequestException;
import com.code10.security.model.report.Count;
import com.code10.security.model.report.Report;
import com.code10.security.repository.AlarmRepository;
import com.code10.security.repository.LogRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final LogRepository logRepository;

    private final AlarmRepository alarmRepository;

    private final TemplateEngine templateEngine;

    public ReportService(LogRepository logRepository,
                         AlarmRepository alarmRepository,
                         @Qualifier("html") TemplateEngine templateEngine) {
        this.logRepository = logRepository;
        this.alarmRepository = alarmRepository;
        this.templateEngine = templateEngine;
    }

    public byte[] getReport(Date startDate, Date endDate) {
        final List<Count> machineLogs = logRepository.countByField("ipAddress", startDate, endDate);
        final List<Object[]> machineAlarms = alarmRepository.countAlarmByMachine(startDate, endDate);

        final List<Count> systemLogs = logRepository.countByField("system", startDate, endDate);
        final List<Object[]> systemAlarms = alarmRepository.countAlarmBySystem(startDate, endDate);

        final Map<String, Report> machineMap =
                machineLogs.stream().collect(Collectors
                        .toMap(Count::getKey, count -> new Report(count.getKey(), count.getCount(), 0)));

        machineAlarms.forEach(objects -> machineMap.get(objects[0]).setAlarmCount((Long) objects[1]));

        final Map<String, Report> systemMap =
                systemLogs.stream().collect(Collectors
                        .toMap(Count::getKey, count -> new Report(count.getKey(), count.getCount(), 0)));

        systemAlarms.forEach(objects -> systemMap.get(objects[0]).setAlarmCount((Long) objects[1]));

        try {
            return createPdf(machineMap.values(), systemMap.values(), startDate, endDate);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

    private byte[] createPdf(Collection<Report> machines, Collection<Report> systems, Date start, Date end) throws Exception {
        final SimpleDateFormat dt = new SimpleDateFormat("MMM dd, yyyy");
        final Context ctx = new Context();
        ctx.setVariable("start", dt.format(start));
        ctx.setVariable("end", dt.format(end));
        ctx.setVariable("machines", machines);
        ctx.setVariable("systems", systems);
        final String processedHtml = templateEngine.process("report.html", ctx);

        FileOutputStream os = null;
        final String fileName = UUID.randomUUID().toString();
        try {
            final File outputFile = File.createTempFile(fileName, ".pdf");
            os = new FileOutputStream(outputFile);

            final ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);
            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            os.close();

            final FileInputStream file = new FileInputStream(outputFile);

            return IOUtils.toByteArray(file);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}
