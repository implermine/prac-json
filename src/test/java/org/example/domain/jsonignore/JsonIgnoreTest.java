package org.example.domain.jsonignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.BaseCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JsonIgnoreTest extends BaseCondition {

    @Test
    @DisplayName("직렬화")
        // -> 직렬화할 때, 무시
    void serialization_test() throws JsonProcessingException {
        Book book = new Book();
        book.setIsbn(1234);
        book.setName("travel");

        String json = objectWriter.writeValueAsString(book);

        System.out.println(json);
        /**
         * {
         *   "name" : "travel"
         * }
         */
    }

    @Test
    @DisplayName("역직렬화") // -> 역직렬화할 때, 무시
    void deserialization_test() throws JsonProcessingException {
        String json = "{\n" +
                "  \"name\" : \"travel\",\n" +
                "  \"isbn\" : 1234\n" +
                "}";

        Book book = objectMapper.readValue(json, Book.class);

        System.out.println(book);
        /**
         * Book(name=travel, isbn=null)
         */
    }
}
