package org.example.annotation.deserialization;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * @JsonAlias JSON 객체는 불안정합니다.
 * 따라서 여러 다른 요구사항에 의해 혹은, Versioning에 의해
 * 서로 다른 이름의 key가 같은 의미로 매핑되야 할 수도 있습니다.
 *
 * 그냥 여러 JsonSetter를 사용하는것을 저는 권장합니다.
 *
 * 표준 Getter가 필요합니다.
 */
public class JsonAliasTest {

    @Test
    public void test1() throws JsonProcessingException {
        String json = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean aliasBean = new ObjectMapper().readerFor(AliasBean.class).readValue(json);

        System.out.println(aliasBean);

    }

    @ToString
    private static class AliasBean {

        @JsonAlias({"fName", "f_name"})
        private String firstName;
        private String lastName;

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}
