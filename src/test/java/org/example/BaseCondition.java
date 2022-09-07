package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public abstract class BaseCondition {

    protected final ObjectMapper objectMapper;
    protected final ObjectWriter objectWriter;

    public BaseCondition() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectWriter = this.objectMapper.writerWithDefaultPrettyPrinter();
    }
}
