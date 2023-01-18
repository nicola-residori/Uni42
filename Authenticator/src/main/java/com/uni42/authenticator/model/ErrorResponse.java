package com.uni42.authenticator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /* - fields - */
    private String resource;
    private String type;
    private Date timestamp;
    private String message;
}
