package org.example.annotation.deserialization;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @JsonAnySetter
 *
 * 여기서 Any 아무거나, 즉 정해지지 않는 '나머지' 의미를 갖습니다.
 *
 * 표준 Setter가 존재하지 않거나,
 * 해당 필드의 AccessLevel이 Public이 아닌 필드라면
 * 해당 어노테이션이 붙은 메서드를 통해 역직렬화를 수행합니다.
 *
 * 보통 Map과 연관되어 많이 사용됩니다.
 */
public class JsonAnySetterTest {


    @Test
    public void test1() throws JsonProcessingException {
        /**
         * {
         *     "name":"My bean",
         *     "attr2":"val2",
         *     "attr1":"val1"
         * }
         */
        String json
                = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";

        ExtendableBean bean = new ObjectMapper().readerFor(ExtendableBean.class).readValue(json);

        System.out.println(bean);
        //{"name":"My bean","attr2":"val2","attr1":"val1"}

    }


    @ToString
    public static class ExtendableBean{
        public String name;
        private Map<String,String> properties;

        public ExtendableBean() {
            this.properties = new HashMap<>();
        }

        @JsonAnySetter
        public void add(String key, String value){
            properties.put(key,value);
        }
    }
}
