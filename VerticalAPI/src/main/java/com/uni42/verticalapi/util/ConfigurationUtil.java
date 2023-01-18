package com.uni42.verticalapi.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class ConfigurationUtil {

    /* - properties - */
    @Value("${configurator.url}")
    private String url;
    @Value("${configurator.connectionTimeout}")
    private Long connectionTimeout;
    @Value("${configurator.readTimeout}")
    private Long readTimeout;

    public String retrieveKey(String key, String defaultValue) {
        String value = defaultValue;
        try {
            Configurator configurator = retrieveConfiguratorFromService(key);
            if (configurator != null && StringUtils.isNotBlank(configurator.getValue())) {
                value = configurator.getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    private Configurator retrieveConfiguratorFromService(String key) {

        /* - create rest template - */
        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(connectionTimeout)).setReadTimeout(Duration.ofSeconds(readTimeout)).build();

        /* - call service - */
        ResponseEntity<Configurator> response = restTemplate.exchange(url + "/" + key, HttpMethod.GET, null, new ParameterizedTypeReference<Configurator>() {
        });
        return response.getBody();
    }
}
