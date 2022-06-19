package org.example.annotation.deserialization;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Assertions;
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
    public void whenDeserializingUsingJsonSetter_thenCorrect()
            throws IOException {

        String json = "{\"id\":1,\"theName\":\"My bean\"}";

        MyBean bean = new ObjectMapper()
                .readerFor(MyBean.class)
                .readValue(json);

        System.out.println(bean);
        // JsonSetterTest.MyBean(id=1, name=My bean)
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
    }
}
