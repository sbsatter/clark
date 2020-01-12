package com.sbsatter.eventprocessor.config;

import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;

public class JsonArrayRecordSeparatorPolicy extends JsonRecordSeparatorPolicy {

    /**
     * Pass the record through. Do nothing.
     *
     * @param record
     * @see RecordSeparatorPolicy#postProcess(String)
     */
    @Override
    public String postProcess(String record) {

        return super.postProcess(record);
    }

    /**
     * Pass the line through.  Do nothing.
     *
     * @param line
     * @see RecordSeparatorPolicy#preProcess(String)
     */
    @Override
    public String preProcess(String line) {
        return super.preProcess(line);
    }
}
