package com.sbsatter.eventprocessor.model;

import lombok.Data;

@Data
public class CompositeModel {
    Customer customer;
    ProductOrder productOrder;
//    Boolean isCustomer = Boolean.FALSE;
}
