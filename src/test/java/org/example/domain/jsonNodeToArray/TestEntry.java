package org.example.domain.jsonNodeToArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.example.BaseCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEntry extends BaseCondition {

    @Test
    @DisplayName("jsonNode to Java Array")
    void test() throws JsonProcessingException {

        String jsonString = "{\n" +
                "    \"people\": [\n" +
                "        {\n" +
                "            \"id\": \"abc\",\n" +
                "            \"name\": \"가나다\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"def\",\n" +
                "            \"name\": \"라마바\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode people = jsonNode.get("people");
        ArrayNode arrayNode = (ArrayNode)people;
        boolean empty = arrayNode.isEmpty();


        System.out.println("stop");
    }

    @Test
    @DisplayName("jsonNode에서 빈 array를 뽑아냈을 때, isEmpty는 어떤가?")
    void test2() throws JsonProcessingException {
        String jsonString = "{\n" +
                "    \"people\": [\n" +
                "    ]\n" +
                "}";

        JsonNode jsonNode1 = objectMapper.readTree(jsonString);
        JsonNode people1 = jsonNode1.get("people");
        boolean empty1 = people1.isEmpty();

        System.out.println("stop");
    }

    @Test
    @DisplayName("jsonNode에서 array가 아닌 값을 뽑아냈을 때, 거기에 isEmpty를 하게되면 어떻게 되는가? -> array가 아니면 그냥 true네" )
    void test3() throws JsonProcessingException {
        String jsonString = "{\n" +
                "    \"people\": [],\n" +
                "    \"pepper\": 123\n" +
                "}";

        JsonNode jsonNode1 = objectMapper.readTree(jsonString);
        JsonNode people1 = jsonNode1.get("pepper");
        boolean empty1 = people1.isEmpty();

        System.out.println("stop");
    }

    @Test
    @DisplayName("jsonNode에서 없는 값을 뽑아내면 어떻게 되는가? -> npe")
    void test4 () throws JsonProcessingException {
        String jsonString = "{\n" +
                "    \"people\": [],\n" +
                "    \"pepper\": 123\n" +
                "}";

        JsonNode jsonNode1 = objectMapper.readTree(jsonString);
        JsonNode people1 = jsonNode1.get("p");
        boolean empty1 = people1.isEmpty();

        System.out.println("stop");
    }

}
