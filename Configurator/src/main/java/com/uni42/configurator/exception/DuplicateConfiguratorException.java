package com.uni42.configurator.exception;

public class DuplicateConfiguratorException extends Exception {

    public DuplicateConfiguratorException(String key) {
        super("Configurator with key=" + key + ", already exists.");
    }
}
