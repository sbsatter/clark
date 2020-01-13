package com.sbsatter.eventprocessor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Event {
    private String id;
    private String aggregateId;
    private Type type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date timestamp;
    private NestedData data;
}
