package com.acs.parking.controller;

import com.acs.parking.dto.LoginCredentialsDto;
import com.acs.parking.model.UserEntity;
import com.acs.parking.service.AuthenticationService;
import com.acs.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }


    @GetMapping("/logged")
    public ResponseEntity<UserEntity> getUserRole(){
        return new ResponseEntity<>(authenticationService.getLoggedUser(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public UserEntity login(@RequestBody LoginCredentialsDto loginCredentials) {
        return authenticationService.login(loginCredentials);
    }

    @PostMapping("/password/reset")
    public UserEntity resetUserPassword(@RequestBody LoginCredentialsDto loginCredentials) {
        return userService.resetPassword(loginCredentials);
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        return new ResponseEntity<>(userService.createUser(userEntity), HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request, response);
    }
}
