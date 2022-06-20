package org.example.annotation.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @JsonProperty
 *
 * We can add the @JsonProperty annotation to indicate the property name in JSON.
 * Serialization - Deserialization 둘다 사용 가능
 *
 * non-standard getter와 와 setter에 사용 가능하다.
 *
 * JsonGetter나 JsonSetter처럼 이 어노테이션에 붙은 이름으로 불안정한 JSON 객체를 만들거나 만들어낸다.
 */
public class JsonPropertyTest {

    @Test
    public void whenUsingJsonProperty_thenCorrect()
            throws IOException {
        MyBean bean = new MyBean(1, "My bean");

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().
                writeValueAsString(bean);

        System.out.println(jsonString);
        /**
         * {
         *   "id" : 1,
         *   "CustomName" : "My bean" <- CustomName으로 직렬화 되었으며 그 직렬화 된 필드를 밑에서 역직렬화에 사용했다.
         * }
         */

        MyBean resultBean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(jsonString);

        System.out.println(resultBean);
        // JsonPropertyTest.MyBean(id=1, name=My bean)
    }

    // ================================================================================================================

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyBean {
        public int id;
        private String name;

        @JsonProperty("CustomName")
        public void setTheName(String name) {
            this.name = name;
        }

        @JsonProperty("CustomName")
        public String getTheName() {
            return name;
        }
    }
}
