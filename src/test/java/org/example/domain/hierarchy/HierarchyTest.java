package org.example.domain.hierarchy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.example.BaseCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HierarchyTest extends BaseCondition {

    @Test
    @DisplayName("직렬화 할때, 어떤 순서로 타는지")
    void serialization_sequence_test() throws JsonProcessingException {

        Animal animal = new Animal();
        Fish fish = new Fish();
        fish.setStringList(List.of("a", "b", "c"));
        animal.setFish(fish);

        System.out.println("====== clear above =======");

        String s = objectWriter.writeValueAsString(animal);

        System.out.println(s);

        /**
         * Animal : getFish() called...   <---- getFish가 먼저 call되었다. 그럴듯함 . 밖에서부터 call하면서 들어가서 결국 immutable 한 값이 무엇인지 확인
         * Fish : getStringList() called...
         * {
         *   "fish" : {
         *     "stringList" : [ "a", "b", "c" ]
         *   }
         * }
         */

    }

    @Test
    @DisplayName("역직렬화 할 때, 무슨 순서로 타는지")
    void deserialization_sequence_test() throws JsonProcessingException {
        String jsonString = "{\n" +
                "  \"fish\" : {\n" +
                "    \"stringList\" : [ \"a\", \"b\", \"c\" ]\n" +
                "  }\n" +
                "}";

        Animal animal = objectMapper.readerFor(Animal.class).readValue(jsonString);

        System.out.println(animal);

        /**
         * Animal : No-arg-constructor called...  // <-- 역직렬화도 밖에서부터 진입
         * Fish : No-arg-constructor called...    // <-- ...
         * Fish : setStringList() called...       // <-- 안에서부터 set
         * Animal : setFish() called...           // <-- 안에서부터 set
         *
         * 여기부턴 @toString
         * Animal : getFish() called...
         * Fish : getStringList() called...
         *
         * Animal(fish=Fish(stringList=[a, b, c]))
         */
    }

    @Test
    @DisplayName("역직렬화 builder로 순서 테스트")
    void test_sequence_for_deserialization() throws JsonProcessingException {
        String jsonString = "{\n" +
                "  \"fish\" : {\n" +
                "    \"stringList\" : [ \"a\", \"b\", \"c\" ]\n" +
                "  }\n" +
                "}";

        Animal2 animal = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
                .readerFor(Animal2.class).readValue(jsonString);

        System.out.println(animal);
        /**
         * 이건 Fish만 Builder
         *
         * Animal : No-arg-constructor called...
         * Fish : All-arg-constructor called...
         * Animal : setFish() called...
         *
         * 여기부터 @ToString
         * Animal : getFish() called...
         * Fish : getStringList() called...
         *
         * Animal2(fish=Fish2(stringList=null))
         */


        /**
         * 이건 Animal , Fish 둘다 Builder
         *
         * Fish : All-arg-constructor called...
         * builder에서 fish 세팅 // <-- 세팅 먼저함
         * Animal : All-arg-constructor called...
         *
         * 여기부터 @ToString
         * Animal : getFish() called...
         * Fish : getStringList() called...
         *
         * Animal2(fish=Fish2(stringList=null))
         */
    }
}
