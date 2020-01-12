package com.sbsatter.eventprocessor.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Date;

@Data
public class Event extends BaseModel {
    private String id;
    private String aggregateId;
    private Type type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss z")
    private Date timestamp;
    private NestedData data;
}
