package com.uni42.channelapi.model;

import com.uni42.channelapi.client.Customer;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Builder
public class CustomerProfile implements Serializable {

    /* - field - */
    private Customer customer;
    private Timestamp lastAccess;
}
