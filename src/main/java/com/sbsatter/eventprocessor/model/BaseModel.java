package com.sbsatter.eventprocessor.model;

import lombok.Data;

@Data
public class BaseModel {
    private String aggregateId;
    private String eventId;
}
