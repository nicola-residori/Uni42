package com.uni42.verticalapi.controller;

import com.uni42.verticalapi.model.Customer;
import com.uni42.verticalapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    /* - service - */
    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<Customer>> retrieve() throws Exception {
        return new ResponseEntity<>(customerService.retrieve(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Customer> retrieve(@PathVariable("username") String username) throws Exception {
        return new ResponseEntity<>(customerService.retrieve(username), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<Customer> create(@RequestBody Customer customer) throws Exception {
        return new ResponseEntity<>(customerService.create(customer), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Customer> update(@PathVariable("username") String username, @RequestBody Customer customer) throws Exception {
        return new ResponseEntity<>(customerService.update(username, customer), HttpStatus.OK);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Customer> delete(@PathVariable("username") String username) throws Exception {
        return new ResponseEntity<>(customerService.delete(username), HttpStatus.OK);
    }

}
