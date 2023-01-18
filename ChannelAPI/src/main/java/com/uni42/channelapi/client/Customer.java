package com.uni42.channelapi.client;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Customer implements Serializable {

    /* - field - */
    private String username;
    private String name;
    private String surname;
    private Date birth;
    private Long accountBalance;
}
