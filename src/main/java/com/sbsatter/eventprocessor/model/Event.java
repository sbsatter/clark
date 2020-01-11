package com.sbsatter.eventprocessor.model;

import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Date;

public class Event extends BaseModel {
    private Type type;
    private Date timestamp;
    private JSONObject data;
}
