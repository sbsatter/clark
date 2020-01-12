package com.sbsatter.eventprocessor.config;

import org.springframework.batch.item.ItemProcessor;

public class EventToObjectMappingProcessor implements ItemProcessor {
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
    public Object process(Object item) throws Exception {
        return item;
    }
}
