package com.sbsatter.eventprocessor.model;

import lombok.Data;

import java.util.Date;

@Data
public class NestedData {
    private String name;
    private Date birthdate;
    private String customerId;
}
