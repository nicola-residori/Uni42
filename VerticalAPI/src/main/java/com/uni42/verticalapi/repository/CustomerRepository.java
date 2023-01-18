package com.uni42.verticalapi.repository;

import com.uni42.verticalapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
