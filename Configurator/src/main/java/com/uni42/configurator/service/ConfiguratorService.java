package com.uni42.configurator.service;

import com.uni42.configurator.exception.ConfiguratorNotFoundException;
import com.uni42.configurator.exception.DuplicateConfiguratorException;
import com.uni42.configurator.model.Configurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uni42.configurator.repository.ConfiguratorRepository;

import java.util.List;

@Service
public class ConfiguratorService {

    /* - repository - */
    @Autowired
    private ConfiguratorRepository configuratorRepository;

    /**
     * @return list of all configurators
     */
    public List<Configurator> retrieve() {
        return configuratorRepository.findAll();
    }

    /**
     * Retrieve specific configurator by its key
     *
     * @param key, configurator identifier
     * @return configurator, null if not found
     * @throws ConfiguratorNotFoundException, when key not found
     */
    public Configurator retrieve(String key) throws ConfiguratorNotFoundException {
        return configuratorRepository.findById(key).orElseThrow(() -> new ConfiguratorNotFoundException(key));
    }

    /**
     * Create configurator
     *
     * @param configurator, object to create
     * @return created configurator
     * @throws DuplicateConfiguratorException, when configurator.key already exists
     */
    public Configurator create(Configurator configurator) throws DuplicateConfiguratorException {

        /* - check if already exists - */
        if (configuratorRepository.findById(configurator.getKey()).isPresent()) {
            throw new DuplicateConfiguratorException(configurator.getKey());
        }

        /* - save on db - */
        return configuratorRepository.save(configurator);
    }

    /**
     * update current configurator found by its key, with configurator
     *
     * @param key,   configurator identifier
     * @param value, new value to set
     * @return old configurator
     * @throws ConfiguratorNotFoundException, when key not found
     */
    public Configurator update(String key, String value) throws ConfiguratorNotFoundException {

        /* - retrieve current configurator by key - */
        Configurator existingConfigurator = configuratorRepository.findById(key).orElseThrow(() -> new ConfiguratorNotFoundException(key));

        /* - clone old to retrieve - */
        Configurator oldConfigurator = existingConfigurator.builder().key(existingConfigurator.getKey()).value(existingConfigurator.getValue()).build();

        /* - save on db - */
        existingConfigurator.setValue(value);
        configuratorRepository.save(existingConfigurator);

        return oldConfigurator;
    }

    /**
     * Delete configurator by its key
     *
     * @param key, configurator identifier
     * @return deleted configurator
     * @throws ConfiguratorNotFoundException, when key not found
     */
    public Configurator delete(String key) throws ConfiguratorNotFoundException {
        /* - check exists - */
        Configurator configurator = configuratorRepository.findById(key).orElseThrow(() -> new ConfiguratorNotFoundException(key));

        /* - delete from db - */
        configuratorRepository.delete(configurator);

        return configurator;
    }
}
