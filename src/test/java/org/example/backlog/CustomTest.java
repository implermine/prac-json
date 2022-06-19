package org.example.backlog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.example.dto.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



class CustomTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
    }

    //기본 생성자만 있음
    @ToString
    private static class Object1{

        private String name;
        private int length;

//        public void setName(String name) {
//            this.name = name;
//        }
    }


    @Test
    @DisplayName("Deserialization할 때, 어떤 method를 사용하는지 확인")
    // no-arg에 setter를 이용한다. (dto.Car 가보면 JsonField로 All-Arg로도 가능)
    public void test1() throws JsonProcessingException {
        String json = "{ \"name\" : \"Taegang\", \"length\" : \"182\" }";

        assertThrows(UnrecognizedPropertyException.class,() -> objectMapper.readValue(json,Object1.class));

        // com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "name" (class org.example.backlog.CustomTest$Object1), not marked as ignorable (0 known properties: ])
        // setter 없으면 위 메시지처럼 없다고 나옴


        //com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "length" (class org.example.backlog.CustomTest$Object1), not marked as ignorable (one known property: "name"])
        // setter 하나만 두면 (위에 주석친 세터처럼) 이런식으로 하나만 안다고 나옴
//        System.out.println(object1);
    }

    @ToString
    private static class Object2{
        private String name;
        private int length;

        public Object2(String name) {
        }
    }

    @Test
    @DisplayName("Deserialization할 때, 기본 생성자 없으면?")
    public void test2() throws JsonProcessingException {
        String json = "{ \"name\" : \"Taegang\", \"length\" : \"182\" }";

        assertThrows(MismatchedInputException.class,() -> objectMapper.readValue(json,Object2.class));

//        System.out.println(object2);

        // com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot construct instance of `org.example.backlog.CustomTest$Object2` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)
        // at [Source: (String)"{ "name" : "Taegang", "length" : "182" }"; line: 1, column: 3]
    }
}
