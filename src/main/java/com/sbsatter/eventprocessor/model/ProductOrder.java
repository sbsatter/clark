package com.sbsatter.eventprocessor.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ProductOrder {
    @Id
//    @Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
    private String aggregateId;
    private String eventId;
    // item name
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private Customer customer;
//    private Customer customer;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderAcceptedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderCancelledAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDeclinedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderFulfilledAt;
}
