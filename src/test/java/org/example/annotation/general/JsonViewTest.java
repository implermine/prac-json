package org.example.annotation.general;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

/**
 * @JsonView
 *
 * Ignore를 여러  Type으로 나눈다.
 * 예를들어 하나의 객체가 Role에 따라 다른 방식으로 Serialize 되어 나가야 된다면
 * 생각해 볼 수 있는 어노테이션이다.
 */
public class JsonViewTest {

    @Test
    public void test1()
            throws JsonProcessingException {
        Item item = new Item(2, "book", "John");

        ObjectWriter objectWriter = new ObjectMapper()
                .writerWithView(Views.Public.class).withDefaultPrettyPrinter();

        String jsonString = objectWriter.writeValueAsString(item);

        System.out.println(jsonString);
        /**
         * {
         *   "id" : 2,
         *   "itemName" : "book"
         * }
         */
    }

    // ================================================================================================================

    public static class Views {
        public static class Public {}
        public static class Internal extends Public {}
    }

    @AllArgsConstructor
    public class Item {
        @JsonView(Views.Public.class)
        public int id;

        @JsonView(Views.Public.class)
        public String itemName;

        @JsonView(Views.Internal.class)
        public String ownerName;
    }
}
