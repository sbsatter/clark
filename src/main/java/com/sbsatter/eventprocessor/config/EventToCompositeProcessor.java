package com.sbsatter.eventprocessor.config;

import com.sbsatter.eventprocessor.model.*;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import com.sbsatter.eventprocessor.repository.ProductOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventToCompositeProcessor implements ItemProcessor<Event, CompositeModel> {

    private final CustomerRepository customerRepository;
    private final ProductOrderRepository productOrderRepository;

    public EventToCompositeProcessor(CustomerRepository customerRepository, ProductOrderRepository productOrderRepository) {
        this.customerRepository = customerRepository;
        this.productOrderRepository = productOrderRepository;
    }

    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing.  If the returned result is null, it is assumed that processing of the item
     * should not continue.
     *
     * @param item to be processed
     * @return potentially modified or new item for continued processing, {@code null} if processing of the
     * provided item should not continue.
     * @throws Exception thrown if exception occurs during processing.
     */
    @Override
    public CompositeModel process(Event item) throws Exception {
//        Thread.sleep(50);
        log.info("*********************************{}*************************************", item.getType());
        log.info("id: {} | timestamp: {}", item.getId(), item.getTimestamp());
        log.info("aggregate id: {}, payload: {}", item.getAggregateId(), item.getData());
        ProductOrder order = new ProductOrder();
        CompositeModel compositeModel = new CompositeModel();
        if (item.getType() == Type.customer_registered) {
            compositeModel.setCustomer(new Customer().fromEvent(item));
            log.info("Customer: {}", compositeModel.getCustomer());
            return compositeModel;
        }

        if (item.getType() == Type.product_ordered) {
//            Customer customer = customerRepository.findByAggregateId(item.getData().getCustomerId());
//            if (customer == null) {
//                log.info("Previous data may not have been saved for customer {}", item.getData().getCustomerId());
//                customer = new Customer();
//                customer.setAggregateId(item.getAggregateId());
//            }
//            log.info("Product bought by customer >> {}", customer);
//            order = new ProductOrder();
            order.setName(item.getData().getName());
            order.setCustomerAggregateId(item.getAggregateId());
//            order.setCustomer(customer);
            order.setOrderedAt(item.getTimestamp());
            order.setEventId(item.getAggregateId());
        } else {
//            order = productOrderRepository.findByAggregateId(item.getAggregateId()).orElse(new ProductOrder());
//            log.info("Order retrieved: {}", order);
//            order.setAggregateId(item.getAggregateId());
        }
        if (item.getType() == Type.order_accepted) {
            order.setOrderAcceptedAt(item.getTimestamp());
        } else if (item.getType() == Type.order_cancelled) {
            order.setOrderCancelledAt(item.getTimestamp());
        } else if (item.getType() == Type.order_declined) {
            order.setOrderDeclinedAt(item.getTimestamp());
        } else if (item.getType() == Type.order_fulfilled) {
            order.setOrderFulfilledAt(item.getTimestamp());
        }

        order.setAggregateId(item.getAggregateId());
        order.setLastUpdatedAt(item.getTimestamp());
        order.setType(item.getType());
        compositeModel.setProductOrder(order);
        return compositeModel;
    }
}
