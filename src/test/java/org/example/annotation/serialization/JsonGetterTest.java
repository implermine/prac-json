package org.example.annotation.serialization;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;


/**
 * @JsonGetter
 *
 * @JsonProperty 와 Alternative 관계
 *
 * 직렬화를 수행 할 Getter를 지정하는 어노테이션 입니다.
 *
 * 직렬화는 게터를 이용해서 수행합니다.
 * 직렬화 할 때 그 Json 객체에 @JsonGetter(x) 의 x가 필드로 생겨난다고 머릿속으로 생각하세요
 */

public class JsonGetterTest {

    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;

    static{
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void test1() throws JsonProcessingException {

        MyBean bean = new MyBean(1, "My Bean");

        String result = objectWriter.writeValueAsString(bean);

        System.out.println(result);
        /**
         * {
         *   "id" : 1,
         *   "name" : "i'm customized"
         * }
         */

    }


    public static class MyBean {
        public int id;
        private String name;

        public MyBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonGetter("name")
        public String getTheCustomizedGetter() {
            return "i'm customized";
        }

        public String getName() {
            return this.name;
        }
    }
}
