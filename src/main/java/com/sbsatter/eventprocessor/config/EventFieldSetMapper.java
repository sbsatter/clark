package com.sbsatter.eventprocessor.config;


import com.sbsatter.eventprocessor.model.Event;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class EventFieldSetMapper implements FieldSetMapper<Event> {
    @Override
    public Event mapFieldSet(FieldSet fieldSet) {
        final Event event = new Event();
        event.setAggregateId(fieldSet.readString("aggregate_id"));
//        event.setTime(fieldSet.readDouble("time"));
        return event;
    }
}

