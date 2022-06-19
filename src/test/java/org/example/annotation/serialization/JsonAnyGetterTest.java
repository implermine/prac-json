package org.example.annotation.serialization;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.AnnotationTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @JsonAnyGetter
 *
 * 직렬화 수행 시, 표준 게터가 없는 애들을 어떻게 직렬화 할지 결정
 * 표준 게터가 없거나 AccessLevel이 Public이 아닌 필드들의 직렬화를 어떤 메서드로 수행할지 결정
 *
 * Map 관련
 */
public class JsonAnyGetterTest {

    @Test
    public void test() throws JsonProcessingException {
        ExtendableBean bean = new ExtendableBean();
        bean.name = "My Bean";
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        String jsonString = new ObjectMapper().writeValueAsString(bean);
        String jsonStringPretty = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bean);

        System.out.println(jsonString);
        //JsonAnyGetter가 없을 경우 -> {"name":"My bean","properties":{"attr2":"val2","attr1":"val1"}} -> depth가 그대로 wrapping됨
        //JsonAnyGetter가 있을 경우 -> {"name":"My bean","attr2":"val2","attr1":"val1"} -> depth가 unwrap됨.

        System.out.println(jsonStringPretty);
        /**
         * {
         *   "name" : "My Bean",
         *   "attr2" : "val2",
         *   "attr1" : "val1"
         * }
         */

    }

    public static class ExtendableBean {
        public String name;
        private Map<String, String> properties;

        public ExtendableBean() {
            this.properties = new HashMap<>();
        }

        @JsonAnyGetter
        public Map<String, String> getProperties() {
            return properties;
        }

        public void add(final String key, final String value) {
            properties.put(key, value);
        }
    }

}
