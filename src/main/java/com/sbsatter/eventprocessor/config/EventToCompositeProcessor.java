package com.sbsatter.eventprocessor.config;

import com.sbsatter.eventprocessor.model.*;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EventToCompositeProcessor implements ItemProcessor<Event, CompositeModel> {

    private CustomerRepository customerRepository;

    public EventToCompositeProcessor(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        CompositeModel compositeModel = new CompositeModel();
        if (item.getType() == Type.customer_registered) {
//            compositeModel.setIsCustomer(Boolean.TRUE);
            compositeModel.setCustomer(new Customer().fromEvent(item));
            return compositeModel;
        }
        ProductOrder order = new ProductOrder();
        order.setAggregateId(item.getAggregateId());
        order.setEventId(item.getAggregateId());
        order.setType(item.getType());
        order.setOrderedAt(item.getTimestamp());
        if (item.getType() == Type.product_ordered) {
            Customer customer = customerRepository.findByEventId(item.getId());
            order.setName(item.getData().getName());
            order.setCustomer(customer);
        } else if (item.getType() == Type.order_accepted) {
            order.setOrderAcceptedAt(item.getTimestamp());
        } else if (item.getType() == Type.order_cancelled) {
            order.setOrderCancelledAt(item.getTimestamp());
        } else if (item.getType() == Type.order_declined) {
            order.setOrderDeclinedAt(item.getTimestamp());
        } else if (item.getType() == Type.order_fulfilled) {
            order.setOrderFulfilledAt(item.getTimestamp());
        }
        compositeModel.setProductOrder(order);
        return compositeModel;
    }
}
