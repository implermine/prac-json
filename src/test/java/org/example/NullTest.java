package org.example;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NullTest extends BaseCondition{

    @Test
    @DisplayName("Serialize null object")
    void test() throws JsonProcessingException {
        Object obj = null;

        String s = objectMapper.writeValueAsString(obj);

        Assertions.assertThat(s).isEqualTo("null"); // not null :X 근데 그럴듯하다
    }

    /**
     * 평문을 역직렬화하면 Exception 발생
     */
    @Test
    @DisplayName("Deserialize string : plaint text")
    void test2() throws JsonProcessingException {

        String someString = "somePlainText";

        org.junit.jupiter.api.Assertions.assertThrows(JsonParseException.class, () -> objectMapper.readValue(someString,Book.class));

    }


    /**
     * "null" 이란 텍스트를 역직렬화하면 Exception 발생안하고(!) null 반환
     */
    @Test
    @DisplayName("Deserialize string: null")
    void test3() throws JsonProcessingException {

        String someString = "null";

        Book book = objectMapper.readValue(someString, Book.class);

        Assertions.assertThat(book).isNull();
    }

    /**
     * "{}" 이란 빈 JSON을 역직렬화하면 Exception 발생안하고, No-Arg-Constructor에 의한 Object 반환
     */
    @Test
    @DisplayName("Deserialize string {}")
    void test4() throws JsonProcessingException{

        String someString = "{}";

        Book book = objectMapper.readValue(someString,Book.class);

        Assertions.assertThat(book).isEqualTo(new Book());

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    static class Book{
        private String name;
        private String isbn;
    }
}
