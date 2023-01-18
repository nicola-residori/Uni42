package com.uni42.authenticator.controller;

import com.uni42.authenticator.model.User;
import com.uni42.authenticator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    /* - service - */
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> retrieve() throws Exception {
        return new ResponseEntity<>(userService.retrieve(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> retrieve(@PathVariable("username") String username) throws Exception {
        return new ResponseEntity<>(userService.retrieve(username), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> create(@RequestParam String username, @RequestParam String password) throws Exception {
        return new ResponseEntity<>(userService.create(username, password), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> changePassword(@PathVariable("username") String username, @RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) throws Exception {
        return new ResponseEntity<>(userService.changePassword(username, currentPassword, newPassword), HttpStatus.OK);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<User> delete(@PathVariable("username") String username) throws Exception {
        return new ResponseEntity<>(userService.delete(username), HttpStatus.OK);
    }

}
