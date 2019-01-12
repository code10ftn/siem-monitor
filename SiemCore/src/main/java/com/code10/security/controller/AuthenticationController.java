package com.code10.security.controller;

import com.code10.security.model.User;
import com.code10.security.model.dto.AuthenticationRequest;
import com.code10.security.model.dto.AuthenticationResponse;
import com.code10.security.security.TokenUtils;
import com.code10.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserService userService;

    private final TokenUtils tokenUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserService userService, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping
    public ResponseEntity authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            userService.handleWrongCredentials(authenticationRequest.getUsername(), request.getRemoteAddr());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final User user = userService.handleCorrectCredentials(userDetails.getUsername());
        final String token = tokenUtils.generateToken(userDetails);
        return new ResponseEntity<>(new AuthenticationResponse(user.getId(), token), HttpStatus.OK);
    }
}
