package org.example.annotation.general;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

/**
 * @JsonFilter
 */
public class JsonFilterTest {

    @Test
    public void test() throws JsonProcessingException {
        BeanWithFilter bean = new BeanWithFilter(1, "My bean");

        FilterProvider filters
                = new SimpleFilterProvider().addFilter(
                "myFilter",
                SimpleBeanPropertyFilter.filterOutAllExcept("name"));

        String result = new ObjectMapper()
                .writer(filters).withDefaultPrettyPrinter()
                .writeValueAsString(bean);

        System.out.println(result);

    }

    @AllArgsConstructor
    @JsonFilter("myFilter")
    public static class BeanWithFilter {
        public int id;
        public String name;
    }
}
