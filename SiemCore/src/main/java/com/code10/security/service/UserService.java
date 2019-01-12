package com.code10.security.service;

import com.code10.security.controller.exception.AuthorizationException;
import com.code10.security.controller.exception.BadRequestException;
import com.code10.security.controller.exception.ForbiddenException;
import com.code10.security.controller.exception.NotFoundException;
import com.code10.security.model.User;
import com.code10.security.model.dto.ChangePasswordRequest;
import com.code10.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final int SIGN_IN_TIMEOUT = 600000;

    private static final int MAX_FAILED_SIGN_INS = 3;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(NotFoundException::new);
    }

    public User findCurrentUser() {
        final UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ForbiddenException("User is not authenticated!");
        }

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return findByUsername(userDetails.getUsername());
    }

    public void updatePassword(User user, ChangePasswordRequest changePasswordRequest) {
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword()) ||
                !changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new BadRequestException("Passwords not matching!");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setLastPasswordReset(new Date());
        userRepository.save(user);
    }

    public void handleWrongCredentials(String username, String ipAddress) {
        final AuthorizationException authorizationException = new AuthorizationException("Wrong credentials!");
        final User user = userRepository.findByUsername(username).orElseThrow(() -> authorizationException);

        if (timeoutPassed(user)) {
            user.setFailedSignIns(0);
        }

        user.setFailedSignIns(Integer.min(user.getFailedSignIns() + 1, MAX_FAILED_SIGN_INS + 1));
        user.setLastFailedSignIn(new Date());
        userRepository.save(user);

        if (user.getFailedSignIns() == MAX_FAILED_SIGN_INS) {
            logger.warn("Account (username={}, IP={}) blocked due to {} failed sign in attempts!", user.getUsername(), ipAddress, MAX_FAILED_SIGN_INS);
            throw new AuthorizationException("Wrong credentials - account locked for 10 minutes!");
        } else {
            throw authorizationException;
        }
    }

    public User handleCorrectCredentials(String username) {
        final AuthorizationException authorizationException = new AuthorizationException("Wrong credentials!");
        final User user = userRepository.findByUsername(username).orElseThrow(() -> authorizationException);

        if (user.getFailedSignIns() >= MAX_FAILED_SIGN_INS && !timeoutPassed(user)) {
            throw authorizationException;
        } else {
            user.setFailedSignIns(0);
            return userRepository.save(user);
        }
    }

    private boolean timeoutPassed(User user) {
        return user.getLastFailedSignIn() == null || new Date().getTime() - user.getLastFailedSignIn().getTime() > SIGN_IN_TIMEOUT;
    }
}
