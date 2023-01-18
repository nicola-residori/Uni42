package com.uni42.channelapi.client;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Date;

@Data
@Builder
public class Customer implements Serializable {

    /* - field - */
    private String username;
    private String name;
    private String surname;
    private Date birth;
    private Long accountBalance;
}
