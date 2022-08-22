package org.example.jsonnode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * https://www.baeldung.com/jackson-json-node-tree-model
 */
public class JsonNodeTest {

    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;

    static {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }


    @Test
    @DisplayName("Construct a Node from Scratch")
    void construct_a_node_from_scratch(){
        ObjectNode objectNode = objectMapper.createObjectNode();
        ObjectNode jsonNodes = JsonNodeFactory.instance.objectNode();
    }

    @Test
    @DisplayName("Parse from a JSON Source")
    void parse_from_a_json_source() throws JsonProcessingException {
        String jsonString = "{\"k1\":\"v1\",\"k2\":\"v2\"}";

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        System.out.println(jsonNode);
        var k1 = jsonNode.get("k1").textValue();
        System.out.println(k1);
    }
}
