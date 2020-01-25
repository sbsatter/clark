package com.sbsatter.eventprocessor.service;

import com.sbsatter.eventprocessor.model.Customer;
import com.sbsatter.eventprocessor.model.ProductOrder;
import com.sbsatter.eventprocessor.model.Type;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import com.sbsatter.eventprocessor.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

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

            ProductOrder savedOrder = productOrderRepository.findByAggregateId(order.getAggregateId()).orElse(new ProductOrder());
            Date timestamp = order.getLastUpdatedAt();
            if (savedOrder.getLastUpdatedAt() != null && savedOrder.getLastUpdatedAt().before(timestamp)) {
            	savedOrder.setType(order.getType());
                savedOrder.setLastUpdatedAt(timestamp);
            }
            if (order.getType() == Type.product_ordered) {
                savedOrder = order;
            } else if (order.getType() == Type.order_accepted) {
                savedOrder.setOrderAcceptedAt(order.getOrderAcceptedAt());
            } else if (order.getType() == Type.order_cancelled) {
                savedOrder.setOrderCancelledAt(order.getOrderCancelledAt());
            } else if (order.getType() == Type.order_declined) {
                savedOrder.setOrderDeclinedAt(order.getOrderDeclinedAt());
            } else if (order.getType() == Type.order_fulfilled) {
                savedOrder.setOrderFulfilledAt(order.getOrderFulfilledAt());
            }

            productOrderRepository.save(savedOrder);
            log.info("Product Order saved >> {}", order);
        }
    }


}
