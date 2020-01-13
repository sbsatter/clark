package com.sbsatter.eventprocessor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String aggregateId;
    private String eventId;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    public Customer fromEvent(Event event) {
        aggregateId = event.getAggregateId();
        eventId = event.getId();
        createdAt = event.getTimestamp();
        name = event.getData().getName();
        birthDate = event.getData().getBirthdate();
        return this;
    }
}
