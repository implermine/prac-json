package org.example.annotation.property_inclusion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

/**
 * @JsonInclude
 *
 * empty / null / default value인 property를 exclude 배제 할 수 있습니다.
 */
public class JsonIncludeTest {


    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MyBean{
        public int id;
        public String name;
    }

    /**
     * 직렬화 시 , null-value는 직렬화 미포함
     */
    @Test
    public void test1() throws JsonProcessingException {
        MyBean bean = new MyBean(1, null);

        // 직렬화
        String result = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(bean);

        System.out.println(result);
        /**
         * {
         *   "id" : 1
         * }
         */

    }

}
