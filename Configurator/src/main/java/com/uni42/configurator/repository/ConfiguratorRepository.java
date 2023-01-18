package com.uni42.configurator.repository;

import com.uni42.configurator.model.Configurator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguratorRepository extends JpaRepository<Configurator, String> {
}
