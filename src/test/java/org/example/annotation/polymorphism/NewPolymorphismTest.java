package org.example.annotation.polymorphism;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.annotation.polymorphism.model.Animal;
import org.example.annotation.polymorphism.model.Dog;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NewPolymorphismTest {

    private static ObjectWriter objectWriter;
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    }


    @Test
    @DisplayName("숨겨진 SubClass를 직렬화")
    void should_show_dtype_for_subclass() throws JsonProcessingException {

        Dog dog = new Dog("winter");
        Animal animal = dog;

        String serialized = objectWriter.writeValueAsString(animal);

        System.out.println(serialized);
        /**
         * {
         *   "imNotType" : "doggy", <------------ dtype 형성
         *   "name" : "winter",
         *   "barkVolume" : 0.0 <----- default
         * }
         */
    }

    @Test
    @DisplayName("dtype이 존재하는 json을 SuperClass로 역직렬화, 그 이후 .getClass()로 실제 타입 확인")
    void should_mapping_subclass_for_dog_when_dtype_is_fitted() throws JsonProcessingException {
        String serialized = "{\n" +
                "  \"imNotType\" : \"doggy\",\n" +
                "  \"name\" : \"winter\",\n" +
                "  \"barkVolume\" : 0.0\n" +
                "}";

        Animal animal = objectMapper.readValue(serialized, Animal.class);

        System.out.println(animal);
        System.out.println(animal.getClass());

        /**
         * Animal(name=winter)
         * class org.example.annotation.polymorphism.model.Dog
         */
    }
}
