package org.example.annotation.serialization;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * @JsonAnyGetter
 *
 * 사실 이 어노테이션 보다 JsonAnySetter 와 같이 쓰여야 의미가 있다.
 * 일반적으로 JSON은 보다 자유롭지만 JAVA Object는 어떤 필드를 사용할지 정하므로 조금 더 제한적이다.
 * 이런 상황에서 자유로운 JSON의 여러 필드가 JAVA Object의 Map으로 매핑되는 행위를 JsonAnySetter가 제한하는 반면
 * JsonAnyGetter는 그의 역만 제공 할 뿐이다.
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
