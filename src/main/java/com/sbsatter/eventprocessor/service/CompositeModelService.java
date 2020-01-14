package com.sbsatter.eventprocessor.service;

import com.sbsatter.eventprocessor.model.Customer;
import com.sbsatter.eventprocessor.model.ProductOrder;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import com.sbsatter.eventprocessor.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompositeModelService {

    private final CustomerRepository customerRepository;
    private final ProductOrderRepository productOrderRepository;

    public CompositeModelService(CustomerRepository customerRepository, ProductOrderRepository productOrderRepository) {
        this.customerRepository = customerRepository;
        this.productOrderRepository = productOrderRepository;
    }

    public void decide(Customer customer, ProductOrder order) {
        if (customer != null) {
            customer = customerRepository.save(customer);
            log.info("Customer saved >> {}", customer);
        } else {
            productOrderRepository.save(order);
            log.info("Product Order saved >> {}", order);
        }
    }


}
