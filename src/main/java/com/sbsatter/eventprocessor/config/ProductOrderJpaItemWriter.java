package com.sbsatter.eventprocessor.config;

import com.sbsatter.eventprocessor.model.*;
import com.sbsatter.eventprocessor.repository.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductOrderJpaItemWriter extends JpaItemWriter<BaseModel> {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public ProductOrderJpaItemWriter() {
        this.setEntityManagerFactory(entityManagerFactory);
    }
    /**
     * Merge all provided items that aren't already in the persistence context
     * and then flush the entity manager.
     *
     * @param items
     * @see ItemWriter#write(List)
     */
    @Override
    public void write(List<? extends BaseModel> items) {
        List<ProductOrder> productOrders = new ArrayList<>();
        items.stream()
                .filter( model -> model instanceof Event)
                .filter(event -> ((Event) event).getType() != Type.customer_registered)
                .forEach(c -> productOrders.add(transform(c)));
        super.write(items);
    }

    private ProductOrder transform(BaseModel model) {
        ProductOrder order = new ProductOrder();
        Event classifiable = (Event) model;
        order.setAggregateId(classifiable.getAggregateId());
        order.setEventId(classifiable.getAggregateId());
        order.setType(classifiable.getType());
        order.setOrderedAt(classifiable.getTimestamp());
        if (classifiable.getType() == Type.product_ordered) {
            Customer customer = customerRepository.findByEventId(classifiable.getId());
            order.setName(classifiable.getData().getName());
            order.setCustomer(customer);
        } else if (classifiable.getType() == Type.order_accepted) {
            order.setOrderAcceptedAt(classifiable.getTimestamp());
        } else if (classifiable.getType() == Type.order_cancelled) {
            order.setOrderCancelledAt(classifiable.getTimestamp());
        } else if (classifiable.getType() == Type.order_declined) {
            order.setOrderDeclinedAt(classifiable.getTimestamp());
        } else if (classifiable.getType() == Type.order_fulfilled) {
            order.setOrderFulfilledAt(classifiable.getTimestamp());
        }
        return order;
    }

}