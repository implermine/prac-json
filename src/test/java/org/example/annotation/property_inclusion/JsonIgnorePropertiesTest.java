package org.example.annotation.property_inclusion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.junit.jupiter.api.Test;

/**
 * @JsonIgnoreProperties
 *
 * class-level annotation 입니다 (class 키워드 위에 붙여야함)
 *
 * jackson이 ignore 할 필드 리스트를 작성하면 됩니다.
 *
 * 직렬-역직렬 둘다 무시합니다.
 *
 * 그나마 유용한건 @JsonIgnoreProperties(ignoreUnknown=true)로 자유로운 JSON 객체에서 JAVA Object가 모르는 필드를
 * exception을 발생시키지 않고 무시할 수 있다는 것입니다.
 */
public class JsonIgnorePropertiesTest {


    /**
     * 직렬화 할때 무시
     */
    @Test
    public void test1() throws JsonProcessingException {
        BeanWithIgnore bean = new BeanWithIgnore(1, "My bean");

        // 직렬화
        String result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bean);

        System.out.println(result);
        /**
         * {
         *   "name" : "My bean"
         * }
         */
    }

    /**
     * 역직렬화 할때 무시
     */
    @Test
    public void test2() throws JsonProcessingException{
        String jsonString = "{\"id\": 2, \"name\": \"implermine\"}";

        BeanWithIgnore bean = new ObjectMapper().readerFor(BeanWithIgnore.class).readValue(jsonString);

        System.out.println(bean);
        // JsonIgnorePropertiesTest.BeanWithIgnore(id=0, name=implermine) -> id가 initialize가 안되었다.

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @JsonIgnoreProperties({ "id" })
    public static class BeanWithIgnore {
        public int id;
        public String name;
    }
}
