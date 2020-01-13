package com.sbsatter.eventprocessor.service;

import com.sbsatter.eventprocessor.model.Customer;
import com.sbsatter.eventprocessor.model.ProductOrder;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import com.sbsatter.eventprocessor.repository.ProductOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class CompositeModelService {

    private final CustomerRepository customerRepository;
    private final ProductOrderRepository productOrderRepository;

    public CompositeModelService(CustomerRepository customerRepository, ProductOrderRepository productOrderRepository) {
        this.customerRepository = customerRepository;
        this.productOrderRepository = productOrderRepository;
    }

    public void decide(Customer customer, ProductOrder order) {
        if (customer != null) {
            customerRepository.save(customer);
        } else {
            productOrderRepository.save(order);
        }
    }


}
