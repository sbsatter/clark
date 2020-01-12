package com.sbsatter.eventprocessor.config;

import com.sbsatter.eventprocessor.model.BaseModel;
import com.sbsatter.eventprocessor.model.Customer;
import com.sbsatter.eventprocessor.model.Event;
import com.sbsatter.eventprocessor.model.Type;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerJpaItemWriter extends JpaItemWriter<BaseModel> {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public CustomerJpaItemWriter() {
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
        List<Customer> customers = new ArrayList<>();
        items.stream()
                .filter( model -> model instanceof Event)
                .filter(event -> ((Event) event).getType() == Type.customer_registered)
                .forEach(c -> customers.add(transform(c)));
        super.write(items);
    }

    private Customer transform(BaseModel model) {
        Customer customer;
        Event classifiable = (Event) model;
        customer = new Customer().fromEvent(classifiable);
        return customer;
    }

}
