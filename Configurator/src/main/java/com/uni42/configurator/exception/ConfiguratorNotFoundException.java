package com.uni42.configurator.exception;

public class ConfiguratorNotFoundException extends Exception {

    public ConfiguratorNotFoundException(String key) {
        super("Configurator with key=" + key + ", not found.");
    }
}
