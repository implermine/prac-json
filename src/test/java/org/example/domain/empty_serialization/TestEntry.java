package org.example.domain.empty_serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.BaseCondition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestEntry extends BaseCondition {

    @Test
    @DisplayName("빈 값을 직렬화 하고 싶을 때 ")
    void test5() throws JsonProcessingException {

        WrappingObject wrappingObject = new WrappingObject();

        /**
         * accessor(a.k.a getter) 가 없을 때, 뻑나버리는 feature를 끄는 행위
         */
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        String s = objectMapper.writeValueAsString(wrappingObject);

        System.out.println(s);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class EmptyObject{}

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WrappingObject{
        private EmptyObject some = new EmptyObject();
    }
}
