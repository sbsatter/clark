package com.sbsatter.eventprocessor.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Customer extends BaseModel {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Temporal(TemporalType.DATE)
    private Date timestamp;


}
