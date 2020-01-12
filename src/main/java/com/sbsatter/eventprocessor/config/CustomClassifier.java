package com.sbsatter.eventprocessor.config;

import com.sbsatter.eventprocessor.model.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class CustomClassifier implements Classifier<Event, JpaItemWriter<? extends BaseModel>> {


    private static final long serialVersionUID = 1L;

    private JpaItemWriter<? extends BaseModel> customerJpaItemWriter;
    private JpaItemWriter<? extends BaseModel> productOrderJpaItemWriter;

    @Autowired
    public CustomClassifier(JpaItemWriter<? extends BaseModel> customerJpaItemWriter, JpaItemWriter<? extends BaseModel> productOrderJpaItemWriter) {
        this.customerJpaItemWriter = customerJpaItemWriter;
        this.productOrderJpaItemWriter = productOrderJpaItemWriter;
    }

    /**
     * Classify the given object and return an object of a different type, possibly an
     * enumerated type.
     *
     * @param classifiable the input object. Can be null.
     * @return an object. Can be null, but implementations should declare if this is the
     * case.
     */
    @Override
    public JpaItemWriter<? extends BaseModel> classify(Event classifiable) {
        if (classifiable.getType() == Type.customer_registered) {
            return customerJpaItemWriter;
        }
        return productOrderJpaItemWriter;
    }
}
