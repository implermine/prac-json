package org.example.annotation.mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;

/**
 * 직렬화 수행 기본 전략을 확인합니다.
 */

/**
 * 직렬화 기본전략은 Public Access -> Getter 입니다.
 *
 * 직렬화 시, 중점은 Getter에 있습니다. 객체의 필드는 그 값을 저장만 할 뿐, JSON과의 interface는 Getter에 있다는것을 생각해주세요.
 */

public class SerializationBasicTest {

    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;

    static {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void test1() throws JsonProcessingException {

        Person person = new Person("taegang" , "implermine", 180);

        String jsonString = objectWriter.writeValueAsString(person);

        System.out.println(jsonString);
        /**
         * {
         *   "name" : "taegang",
         *   "publicName" : "implermine"
         *   -> height는 Getter가 없어서 직렬화 되지 않음
         * }
         */

    }


    // =================================================================================================================
    // 사용 객체

    static class Person{
        private String name;
        public String publicName;
        private int height;

        public Person(String name, String publicName, int height) {
            this.name = name;
            this.publicName = publicName;
            this.height = height;
        }

        public String getName(){
            return this.name;
        }

        public int amIGetter(){
            return this.height;
        }
    }
}
