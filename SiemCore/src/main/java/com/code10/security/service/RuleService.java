package com.code10.security.service;

import com.code10.security.controller.exception.BadRequestException;
import com.code10.security.controller.exception.NotFoundException;
import com.code10.security.model.*;
import com.code10.security.repository.RuleFileRepository;
import com.code10.security.repository.RuleRepository;
import com.code10.security.util.Util;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains rules CRUD methods and everything rule engine related.
 * After any CRUD operation the rules repository is refreshed using Maven,
 * so that the changes are also applied to the running kie session without needing to recreate it.
 */
@Service
public class RuleService {

    private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

    // Path to Maven wrapper, used for sending Maven commands programmatically.
    private static final String MAVEN_WRAPPER_PATH = Paths.get("bin", "mvnw").toString();

    // Path to Maven configuration file of the rules repository.
    private static final String POM_PATH = Paths.get("..", "SiemRules", "pom.xml").toString();

    // Command prompt expression for refreshing the rules repository.
    private static final String MAVEN_COMMANDS = String.format("cmd.exe /C \"%s -f %s clean install\"", MAVEN_WRAPPER_PATH, POM_PATH);

    // Default regex to match any string.
    private static final String REGEX_ANY = "(?s).*";

    private final KieSession kieSession;

    private final RuleRepository ruleRepository;

    private final RuleFileRepository ruleFileRepository;

    private final TemplateEngine templateEngine;

    private final AlarmService alarmService;

    private final NotificationService notificationService;

    @Autowired
    public RuleService(KieSession kieSession, RuleRepository ruleRepository, RuleFileRepository ruleFileRepository, TemplateEngine templateEngine, AlarmService alarmService, NotificationService notificationService) {
        this.kieSession = kieSession;
        this.ruleRepository = ruleRepository;
        this.ruleFileRepository = ruleFileRepository;
        this.templateEngine = templateEngine;
        this.alarmService = alarmService;
        this.notificationService = notificationService;
    }

    public AlarmRule create(AlarmRule rule) {
        setDefaultMatchers(rule);
        rule = ruleRepository.save(rule);
        ruleFileRepository.save(rule.getId(), parseRule(rule));

        reloadRules();

        return rule;
    }

    public Page<AlarmRule> findAll(Pageable pageable) {
        return ruleRepository.findAll(pageable);
    }

    public List<AlarmRule> findAll() {
        return ruleRepository.findAll();
    }

    public AlarmRule findById(long id) {
        return ruleRepository.findById(id).orElseThrow(() -> new NotFoundException("Rule not found!"));
    }

    public void update(AlarmRule rule, long id) {
        setDefaultMatchers(rule);
        ruleRepository.findById(id).orElseThrow(() -> new NotFoundException("Rule not found!"));
        rule.setId(id);

        ruleRepository.save(rule);
        ruleFileRepository.save(id, parseRule(rule));

        reloadRules();
    }

    public void delete(long id) {
        ruleRepository.findById(id).orElseThrow(() -> new NotFoundException("Rule not found!"));

        ruleRepository.deleteById(id);
        ruleFileRepository.deleteById(id);

        reloadRules();
    }

    public void deleteAll() {
        ruleRepository.deleteAll();
        ruleFileRepository.deleteAll();

        reloadRules();
    }

    /**
     * Adds an event to the running kie session upon adding a new log item and fires all rules to check for alarms.
     *
     * @param logEvent event to be added to kie session
     */
    public void updateSession(LogEvent logEvent) {
        final List<AlarmRule> rules = findAll();

        for (AlarmRule rule : rules) {
            if (eventMatchesRule(logEvent, rule)) {
                kieSession.insert(logEvent);
                kieSession.fireAllRules();

                return;
            }
        }
    }

    private boolean eventMatchesRule(LogEvent logEvent, AlarmRule rule) {
        return logEvent.getLogItem().getIpAddress().matches(rule.getLogIpAddress()) &&
                logEvent.getLogItem().getHostName().matches(rule.getLogHostName()) &&
                logEvent.getLogItem().getSourceName().matches(rule.getLogSourceName()) &&
                logEvent.getLogItem().getProcessId().matches(rule.getLogProcessId()) &&
                logEvent.getLogItem().getFacility().matches(rule.getLogFacility()) &&
                logEvent.getLogItem().getSeverity().name().matches(rule.getLogSeverity()) &&
                logEvent.getLogItem().getSystem().matches(rule.getLogSystem()) &&
                logEvent.getLogItem().getMessage().matches(rule.getLogMessage()) &&
                logEvent.getLogItem().getRaw().matches(rule.getLogRaw());
    }

    /**
     * Called from RHS of alarm rules.
     *
     * @param ruleId         id of rule for which the alarm was triggered
     * @param matchingEvents events that triggered the alarm
     */
    @SuppressWarnings("unused")
    public void fireAlarm(long ruleId, List<LogEvent> matchingEvents) {
        final AlarmRule rule = findById(ruleId);
        final List<LogItem> matchingLogs = matchingEvents.stream().map(LogEvent::getLogItem).collect(Collectors.toList());

        if (alarmExists(rule, matchingLogs)) {
            return;
        }

        final Alarm alarm = new Alarm();
        alarm.setRule(rule);
        alarmService.create(alarm);
        alarm.setLogs(matchingLogs);
        alarm.setHashCode(alarm.hashCode());
        alarmService.create(alarm);

        if (rule.getPriority() == AlarmPriority.MEDIUM || rule.getPriority() == AlarmPriority.HIGH) {
            logger.warn("{} fired alarm!", rule.getName());
            notificationService.sendNotification(String.format("%s fired alarm!", rule.getName()));
        }
    }

    private boolean alarmExists(AlarmRule rule, List<LogItem> logs) {
        final Alarm alarm = new Alarm(rule, logs);
        return alarmService.findByHashCode(alarm.hashCode()).isPresent();
    }

    /**
     * Creates the text for a Drools rule file.
     *
     * @param rule rule parameters
     * @return parsed rule
     */
    private String parseRule(AlarmRule rule) {
        final Context context = new Context();

        context.setVariable("ruleName", rule.getName());
        context.setVariable("ruleId", rule.getId());
        context.setVariable("repetitions", rule.getRepetitions());
        context.setVariable("interval", rule.getInterval());

        context.setVariable("logIpAddress", rule.getLogIpAddress());
        context.setVariable("logHostName", rule.getLogHostName());
        context.setVariable("logSourceName", rule.getLogSourceName());
        context.setVariable("logProcessId", rule.getLogProcessId());
        context.setVariable("logFacility", rule.getLogFacility());
        context.setVariable("logSeverity", rule.getLogSeverity());
        context.setVariable("logSystem", rule.getLogSystem());
        context.setVariable("logMessage", rule.getLogMessage());
        context.setVariable("logRaw", rule.getLogRaw());

        context.setVariable("logIpAddressRepeated", rule.isLogIpAddressRepeated() ? ", $i.ipAddress == $baseEvent.logItem.ipAddress" : "");
        context.setVariable("logHostNameRepeated", rule.isLogHostNameRepeated() ? ", $i.hostName == $baseEvent.logItem.hostName" : "");
        context.setVariable("logSourceNameRepeated", rule.isLogSourceNameRepeated() ? ", $i.sourceName == $baseEvent.logItem.sourceName" : "");
        context.setVariable("logProcessIdRepeated", rule.isLogProcessIdRepeated() ? ", $i.processId == $baseEvent.logItem.processId" : "");
        context.setVariable("logFacilityRepeated", rule.isLogFacilityRepeated() ? ", $i.facility == $baseEvent.logItem.facility" : "");
        context.setVariable("logSeverityRepeated", rule.isLogSeverityRepeated() ? ", $i.severity == $baseEvent.logItem.severity" : "");
        context.setVariable("logMessageRepeated", rule.isLogMessageRepeated() ? ", $i.message == $baseEvent.logItem.message" : "");
        context.setVariable("logRawRepeated", rule.isLogRawRepeated() ? ", $i.raw == $baseEvent.logItem.raw" : "");

        return templateEngine.process("rule.txt", context);
    }

    private void setDefaultMatchers(AlarmRule rule) {
        rule.setLogIpAddress(getOrDefault(rule.getLogIpAddress()));
        rule.setLogHostName(getOrDefault(rule.getLogHostName()));
        rule.setLogSourceName(getOrDefault(rule.getLogSourceName()));
        rule.setLogProcessId(getOrDefault(rule.getLogProcessId()));
        rule.setLogFacility(getOrDefault(rule.getLogFacility()));
        rule.setLogSeverity(getOrDefault(rule.getLogSeverity()));
        rule.setLogSystem(getOrDefault(rule.getLogSystem()));
        rule.setLogMessage(getOrDefault(rule.getLogMessage()));
        rule.setLogRaw(getOrDefault(rule.getLogRaw()));
    }

    private String getOrDefault(String s) {
        return Util.nullOrEmpty(s) ? REGEX_ANY : s;
    }

    private void reloadRules() {
        try {
            final Process process = Runtime.getRuntime().exec(MAVEN_COMMANDS);
            final BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            final String error;
            if ((error = errorStream.readLine()) != null) {
                throw new RuntimeException(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error updating rules project!");
        }
    }
}
