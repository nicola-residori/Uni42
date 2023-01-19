package com.uni42.channelapi.client;

import com.uni42.channelapi.util.ConfigurationUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class VerticalClient {

    /* - constant - */
    public static final String VERTICAL_SERVICE_URL = "http://localhost:8083";
    public static final String CUSTOMER_CONTEXT_ROOT = "/customer";

    /* - utility - */
    @Autowired
    private ConfigurationUtil configurationUtil;

    /**
     * call verticalService to retrieve customer
     *
     * @param username, customer identifier
     * @return customer
     * @throws Exception, when something goes wrong during calling service
     */
    @CircuitBreaker(name = "vertical_customer")
    public Customer retrieveCustomer(String username) throws Exception {

        /* - retrieve service config - */
        String url = configurationUtil.retrieveKey("vertical.url", VERTICAL_SERVICE_URL) + CUSTOMER_CONTEXT_ROOT;
        long connectionTimeout = Long.parseLong(configurationUtil.retrieveKey("vertical.customer.connectionTimeout", "1"));
        long readTimeout = Long.parseLong(configurationUtil.retrieveKey("vertical.customer.readTimeout", "10"));

        /* - create rest template - */
        RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(connectionTimeout)).setReadTimeout(Duration.ofSeconds(readTimeout)).build();

        /* - call service - */
        ResponseEntity<Customer> response = restTemplate.exchange(url + "/" + username, HttpMethod.GET, null, new ParameterizedTypeReference<Customer>() {
        });
        return response.getBody();
    }
}
