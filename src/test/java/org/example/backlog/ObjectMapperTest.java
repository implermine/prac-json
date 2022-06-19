package org.example.backlog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.assertj.core.api.Assertions;
import org.example.dto.Car;
import org.example.dto.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

// https://www.baeldung.com/jackson-object-mapper-tutorial
class ObjectMapperTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void beforeEach() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Java Object to JSON")
    public void test1() throws JsonProcessingException {

        Car car = new Car("yellow", "renault");

        String carToString = objectMapper.writeValueAsString(car);

        System.out.println("carToString = " + carToString);

        assertThat(carToString).isEqualTo("{\"color\":\"yellow\",\"type\":\"renault\"}");
    }

    @Test
    @DisplayName("JSON to Java Object")
    public void test2() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = objectMapper.readValue(json, Car.class);

        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }

    @Test
    @DisplayName("JSON to Jackson JsonNode")
    public void test3() throws JsonProcessingException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();

        assertThat(color).isEqualTo("Black");
    }

    @Test
    @DisplayName("Creating a Java List From a JSON Array String")
    public void test4() throws JsonProcessingException {
        String jsonCarArray =
                "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";

        List<Car> carList = objectMapper.readValue(jsonCarArray, new TypeReference<>() {
        });

        assertThat(carList.stream().anyMatch(e -> e.getColor().equals("Black"))).isTrue();
    }

    @Test
    @DisplayName("Configuring Serialization or Deserialization Feature")
    public void test5() {

        // JSON 객체를 Java Class로 변환할때, JSON 객체에 New(새로운) 필드가 존재한다면,
        // Default process론 exception이 발생 할 것입니다.

        String jsonString
                = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

        assertThrows(UnrecognizedPropertyException.class, () -> objectMapper.readValue(jsonString, Car.class));

    }

    @Test
    @DisplayName("JSON 객체에는 있는 필드가 Java객체에는 없다면 어떤식으로 처리 할 수 있는지")
    public void test6() throws JsonProcessingException {
        String jsonString
                = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Car car = objectMapper.readValue(jsonString, Car.class);

        JsonNode jsonNodeRoot = objectMapper.readTree(jsonString);
        JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        String year = jsonNodeYear.asText();
    }

    @Test
    @DisplayName("JSON객체에는 없는 필드가 Java객체에는 있다면")
    public void test7() throws JsonProcessingException {
//        String jsonString
//                = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"primitiveInt\" : \"null\" }";
//
//        // objectMapper
//        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
//
//        Car car = objectMapper.readValue(jsonString, Car.class);

//        System.out.println(car.getPrimitiveInt());
//        System.out.println(car.getWrapperInt());
    }

    @Test
    @DisplayName("ENUM unmarshalling")
    public void test8() throws JsonProcessingException{
        String jsonString
                = "{ \"name\" : \"Tom\", \"height\" : \"178\", \"job\" : \"Dentist\" }";

        Person person = objectMapper.readValue(jsonString, Person.class);

        System.out.println(person);
    }

    @Test
    @DisplayName("ENUM unmarshalling-2")
    public void test9() throws JsonProcessingException{
        String jsonString
                = "{ \"name\" : \"Tom\", \"height\" : \"178\", \"job\" : \"3\" }";

        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        Person person = objectMapper.readValue(jsonString, Person.class);

        System.out.println(person);
    }


}
