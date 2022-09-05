package org.example.annotation.deserialization;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @JsonSetter
 *
 * 해당 어노테이션의 파라미터를 직렬화 데이터에서 찾아서
 * 해당 세터의 파라미터로 사용
 *
 * 역직렬화를 수행 할 Setter를 정합니다.
 * 해당 어노테이션의 파라미터론, 역직렬화 될 직렬화 데이터의 key값을 넣습니다.
 *
 * 역직렬화 될 객체필드의 변수명을 바꾸기 어려울 때 사용합니다.
 */
public class JsonSetterTest {


    @Test
    public void test1()
            throws IOException {

        String json = "{\"id\":1,\"theName\":\"My bean\"}";

        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);

        System.out.println(bean);
        // JsonSetterTest.MyBean(id=1, name=My bean)
    }

    @Test
    public void test2() throws JsonProcessingException {
        String json = "{\"id\":1,\"theSecondName\":\"My bean\"}";

        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);

        System.out.println(bean);
        // JsonSetterTest.MyBean(id=1, name=My bean)
    }

    @Test
    @DisplayName("직렬화시 @JsonProperty 이용?")
    public void test3() throws JsonProcessingException{
        String json = "{\"id\":1,\"theSecondName\":\"My bean\"}";

        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);

        System.out.println(bean);
        // JsonSetterTest.MyBean(id=1, name=My bean)

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(bean);

        System.out.println(s);
        // {"id":1,"name":"My bean"}
    }


    @Getter
    @ToString
    static class MyBean{
        public int id;
        private String name;

        @JsonSetter("theName")
        public void amISetterReally(String name){
            this.name = name;
        }

        @JsonProperty("theSecondName") // -> JsonAlias과 같은 효과
        public void dragon(String name){
            this.name = name;
        }
    }
}
