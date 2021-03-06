package com.sbsatter.eventprocessor.repository;

import com.sbsatter.eventprocessor.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<ProductOrder, Integer> {

}
