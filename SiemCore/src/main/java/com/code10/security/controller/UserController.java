package com.code10.security.controller;

import com.code10.security.model.User;
import com.code10.security.model.dto.ChangePasswordRequest;
import com.code10.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        final User currentUser = userService.findCurrentUser();

        userService.updatePassword(currentUser, changePasswordRequest);

        return new ResponseEntity<>(currentUser.getId(), HttpStatus.OK);
    }
}
