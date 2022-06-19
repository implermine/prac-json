package org.example.annotation.deserialization;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * @JsonCreator
 *
 *
 * unmarshalling을 수행 할 기본 생성자를 정한다.
 *
 * 원래 역직렬화는 기본생성자+세터로 수행하는데, 생성자로 unmarshalling을 하게끔 한다.
 */
public class JsonCreatorTest {

    private static ObjectMapper objectMapper;
    private static ObjectWriter prettyMapper;

    static{
        objectMapper = new ObjectMapper();
        prettyMapper = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    @Test
    public void JsonCreatorTest() throws JsonProcessingException {
        String jsonString = "{\"id\":1,\"theName\":\"My bean\"}";

        BeanWithCreator bean = objectMapper.readValue(jsonString, BeanWithCreator.class);


        System.out.println(bean);
        // BeanWithCreator(id=1, name=My bean)

        /**
         * BeanWithCreator의 필드인 name은 주어진 jsonString인 theName과 매칭이 안되어야 하지만,
         * JsonProperty에 의해 매칭됨.
         */
    }

    @ToString
    static class BeanWithCreator{
        public int id;
        public String name;

        @JsonCreator
        public BeanWithCreator(@JsonProperty("id") int id, @JsonProperty("theName") String name){
            this.id = id;
            this.name = name;
        }
    }



}

