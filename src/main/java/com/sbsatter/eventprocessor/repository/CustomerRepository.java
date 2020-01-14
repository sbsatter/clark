package com.sbsatter.eventprocessor.repository;

import com.sbsatter.eventprocessor.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByAggregateId(String id);
}
