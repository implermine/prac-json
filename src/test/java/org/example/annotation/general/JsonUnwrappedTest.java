package org.example.annotation.general;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;

/**
 * @JsonUnwrapped
 *
 * 반대로 JsonWrapped가 사용해지고 싶다면 JsonRootName을 참조
 */
public class JsonUnwrappedTest {

    @Test
    public void test1() throws JsonProcessingException {
        UnwrappedUser.Name name = new UnwrappedUser.Name("John", "Doe");
        UnwrappedUser.Name nameNoAnnotation = new UnwrappedUser.Name("Lee", "Taegang");

        UnwrappedUser user = new UnwrappedUser(1, name, nameNoAnnotation);

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user);

        System.out.println(jsonString);

        /**
         * {
         *   "id" : 1,
         *   "firstName" : "John", <- Unwrapped !
         *   "lastName" : "Doe",
         *   "nameNoAnnotation" : {
         *     "firstName" : "Lee",
         *     "lastName" : "Taegang"
         *   }
         * }
         */



    }



    // =================================================================================================================

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UnwrappedUser {
        public int id;

        @JsonUnwrapped
        public Name name;
        public Name nameNoAnnotation;

        @AllArgsConstructor
        public static class Name {
            public String firstName;
            public String lastName;
        }

    }

}
