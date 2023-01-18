package com.uni42.verticalapi.service;

import com.uni42.verticalapi.exception.CustomerNotFoundException;
import com.uni42.verticalapi.exception.DuplicateCustomerException;
import com.uni42.verticalapi.model.Customer;
import com.uni42.verticalapi.repository.CustomerRepository;
import com.uni42.verticalapi.util.ConfigurationUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    /* - repository - */
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ConfigurationUtil configurationUtil;

    /**
     * @return list of all customers
     */
    public List<Customer> retrieve() {
        return customerRepository.findAll();
    }

    /**
     * retrieve customer by its username
     *
     * @param username, customer identifier
     * @return customer
     * @throws CustomerNotFoundException, when username not found
     */
    public Customer retrieve(String username) throws CustomerNotFoundException {
        sleep();
        return customerRepository.findById(username).orElseThrow(() -> new CustomerNotFoundException(username));
    }

    /**
     * create customer
     *
     * @param customer, object to create
     * @return created customer
     * @throws DuplicateCustomerException, when customer#username already exists
     */
    public Customer create(Customer customer) throws DuplicateCustomerException {

        /* - check if already exists - */
        if (customerRepository.findById(customer.getUsername()).isPresent()) {
            throw new DuplicateCustomerException(customer.getUsername());
        }

        /* - save on db - */
        return customerRepository.save(customer);
    }

    /**
     * update customer by its username, with customer object
     *
     * @param username, customer identifier
     * @param customer, new customer to update
     * @return old customer
     * @throws CustomerNotFoundException, when customer#username not found
     */
    public Customer update(String username, Customer customer) throws CustomerNotFoundException {

        /* - retrieve current customer - */
        Customer currentCustomer = customerRepository.findById(username).orElseThrow(() -> new CustomerNotFoundException(username));

        /* - clone old to return - */
        Customer oldCustomer = currentCustomer.builder()
                .username(currentCustomer.getUsername())
                .name(currentCustomer.getName())
                .surname(currentCustomer.getSurname())
                .birth(currentCustomer.getBirth())
                .accountBalance(currentCustomer.getAccountBalance())
                .build();

        /* - load new data - */
        if (StringUtils.isNotBlank(customer.getName())) {
            currentCustomer.setName(customer.getName());
        }
        if (StringUtils.isNotBlank(customer.getSurname())) {
            currentCustomer.setSurname(customer.getSurname());
        }
        if (customer.getBirth() != null) {
            currentCustomer.setBirth(customer.getBirth());
        }
        if (customer.getAccountBalance() != null) {
            currentCustomer.setAccountBalance(customer.getAccountBalance());
        }

        /* - save on db - */
        customerRepository.save(currentCustomer);

        return oldCustomer;
    }

    /**
     * delete customer by its username
     *
     * @param username, customer identifier
     * @return delete customer
     * @throws CustomerNotFoundException, when customer#username not found
     */
    public Customer delete(String username) throws CustomerNotFoundException {

        /* - check exists - */
        Customer customer = customerRepository.findById(username).orElseThrow(() -> new CustomerNotFoundException(username));

        /* - delete from db - */
        customerRepository.delete(customer);

        return customer;
    }

    /**
     * perform sleep to simulate slowdown of service
     */
    private void sleep() {
        /* - retrieve sleep time in millis - */
        Long sleepTimeInMillis = Long.parseLong(configurationUtil.retrieveKey("sleep", "-1"));
        if (sleepTimeInMillis > 0) {
            try {
                Thread.sleep(sleepTimeInMillis);
            } catch (InterruptedException e) {
            }
        }
    }
}
