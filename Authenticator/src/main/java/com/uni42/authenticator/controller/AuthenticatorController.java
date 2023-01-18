package com.uni42.authenticator.controller;

import com.uni42.authenticator.service.AuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticatorController {

    @Autowired
    private AuthenticatorService authenticatorService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        return new ResponseEntity<>(authenticatorService.login(username, password), HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam("username") String username, @RequestParam("jwtToken") String jwtToken) throws Exception {
        return new ResponseEntity<>(authenticatorService.validate(username, jwtToken), HttpStatus.OK);
    }
}
