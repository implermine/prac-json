package org.example.domain.jsonignore;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Test
    @DisplayName("빌더를 사용한 역직렬화") // -> 역직렬화할 때, 그대로(위와같은 No-arg + setter 처럼) 무시
    void deserialization_test_for_builder() throws JsonProcessingException {
        String json = "{\n" +
                "  \"name\" : \"travel\",\n" +
                "  \"isbn\" : 1234\n" +
                "}";

        Book2 book = objectMapper.readValue(json, Book2.class);

        System.out.println(book);
        /**
         * Book(name=travel, isbn=null)
         */
    }

    @Test
    @DisplayName("@JsonProperty를 이용한 직렬화")
    void serialization_test_for_jsonProperty() throws JsonProcessingException{
        Book3 book = new Book3();
        book.setIsbn(1234);
        book.setName("travel");

        String json = objectWriter.writeValueAsString(book);

        System.out.println(json);
        /**
         * {
         *   "isbn" : 1234 // name은 직렬화 무시
         * }
         */
    }

    @Test
    @DisplayName("@JsonProperty를 이용한 역직렬화")
    void deserialization_test_for_jsonProperty() throws JsonProcessingException{
        String json = "{\n" +
                "  \"name\" : \"travel\",\n" +
                "  \"isbn\" : 1234\n" +
                "}";

        Book3 book = objectMapper.readValue(json, Book3.class);

        System.out.println(book);

        /**
         * Book3(name=travel, isbn=null) // isbn은 역직렬화가 안되었다.
         */
    }
}
