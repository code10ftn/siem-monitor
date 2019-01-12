package com.code10.security;

import com.code10.security.model.Permission;
import com.code10.security.model.Role;
import com.code10.security.model.User;
import com.code10.security.repository.UserRepository;
import com.code10.security.service.RuleService;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.code10.security.security.PermissionConstants.*;
import static com.code10.security.security.RoleConstants.ADMIN;
import static com.code10.security.security.RoleConstants.OPERATOR;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final RuleService ruleService;

    private final KieSession kieSession;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RuleService ruleService, KieSession kieSession, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.ruleService = ruleService;
        this.kieSession = kieSession;
        this.passwordEncoder = passwordEncoder;
    }

    public void run(ApplicationArguments args) {
        kieSession.insert(ruleService);

        if (userRepository.findAll().isEmpty()) {
            initDb();
        }
    }

    private void initDb() {
        final Permission canGetLogPermission = new Permission(CAN_GET_LOG);
        final Permission canGetRulePermission = new Permission(CAN_GET_RULE);
        final Permission canPostRulePermission = new Permission(CAN_POST_RULE);
        final Permission canPutRulePermission = new Permission(CAN_PUT_RULE);
        final Permission canDeleteRulePermission = new Permission(CAN_DELETE_RULE);
        final Permission canGetAlarmPermission = new Permission(CAN_GET_ALARM);
        final Permission canGetReportPermission = new Permission(CAN_GET_REPORT);

        initializeUser("admin", ADMIN, canGetLogPermission, canGetRulePermission, canPostRulePermission, canPutRulePermission, canDeleteRulePermission, canGetAlarmPermission, canGetReportPermission);
        initializeUser("operator", OPERATOR, canGetLogPermission, canGetAlarmPermission, canGetReportPermission);
    }

    private void initializeUser(String username, String roleName, Permission... permissions) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(username));
        user.setLastPasswordReset(new Date());

        final List<Role> roles = new ArrayList<>();
        final Role role = new Role(roleName);
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        role.setPermissions(Arrays.asList(permissions));

        userRepository.save(user);
    }
}
