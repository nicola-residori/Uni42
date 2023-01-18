package com.uni42.configurator.controller;

import com.uni42.configurator.model.Configurator;
import com.uni42.configurator.service.ConfiguratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("configurator")
public class ConfiguratorController {

    /* - service - */
    @Autowired
    private ConfiguratorService configuratorService;

    @GetMapping()
    public ResponseEntity<List<Configurator>> retrieve() throws Exception {
        return new ResponseEntity<>(configuratorService.retrieve(), HttpStatus.OK);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Configurator> retrieve(@PathVariable("key") String key) throws Exception {
        return new ResponseEntity<>(configuratorService.retrieve(key), HttpStatus.OK);
    }

    @GetMapping("/value/{key}")
    public ResponseEntity<String> retrieveValue(@PathVariable("key") String key) throws Exception {
        return new ResponseEntity<>(configuratorService.retrieve(key).getValue(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Configurator> create(@RequestBody Configurator configurator) throws Exception {
        return new ResponseEntity<>(configuratorService.create(configurator), HttpStatus.OK);
    }

    @PostMapping("/value")
    public ResponseEntity<Configurator> create(@RequestParam("key") String key, @RequestParam("value") String value) throws Exception {
        return new ResponseEntity<>(configuratorService.create(Configurator.builder().key(key).value(value).build()), HttpStatus.OK);
    }

    @PutMapping("/{key}")
    public ResponseEntity<Configurator> update(@PathVariable("key") String key, @RequestParam("value") String value) throws Exception {
        return new ResponseEntity<>(configuratorService.update(key, value), HttpStatus.OK);
    }

    @DeleteMapping("{key}")
    public ResponseEntity<Configurator> delete(@PathVariable("key") String key) throws Exception {
        return new ResponseEntity<>(configuratorService.delete(key), HttpStatus.OK);
    }

}
