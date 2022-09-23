package org.example.domain.getter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.BaseCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEntry extends BaseCondition {

    @Test
    @DisplayName("...")
    void test() throws JsonProcessingException {
        Book book = new Book("hawk", 1234);

        String s = objectWriter.writeValueAsString(book);

        System.out.println(s);

        /**
         * {
         *   "name" : "hawk",
         *   "isbn" : 1234,
         *   "name2" : "hawk"
         * }
         */
    }
}
