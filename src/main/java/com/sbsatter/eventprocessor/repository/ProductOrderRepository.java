package com.sbsatter.eventprocessor.repository;

import com.sbsatter.eventprocessor.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, Integer> {
    Optional<ProductOrder> findByAggregateId(String aggregateId);
}
