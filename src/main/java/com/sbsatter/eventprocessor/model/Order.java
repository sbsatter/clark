package com.sbsatter.eventprocessor.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Order extends BaseModel {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // item name
    private String name;
    @ManyToOne
    private Customer customer;

    @Temporal(TemporalType.DATE)
    private Date orderedAt;
    @Temporal(TemporalType.DATE)
    private Date orderAcceptedAt;
    @Temporal(TemporalType.DATE)
    private Date orderCancelledAt;
    @Temporal(TemporalType.DATE)
    private Date orderDeclinedAt;
    @Temporal(TemporalType.DATE)
    private Date orderFulfilledAt;



}
