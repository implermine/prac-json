package org.example.annotation.polymorphism;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

public class PolymorphismLosingDataTest {

    @Test
    public void test1() throws JsonProcessingException {
        Person person = new Person.Man("implermine",180, Person.Gender.MAN);

        System.out.println(person.getClass());
        //class org.example.annotation.polymorphism.PolymorphismLosingDataTest$Person$Man

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(person);

        System.out.println(jsonString);

        /**
         * {
         *   "name" : "implermine",
         *   "height" : 180,
         *   "gender" : "MAN" <- Data are not losing...!
         * }
         */
    }


    //==================================================================================================================

    @Getter
    @Setter
    public static class Person{
        private String name;
        private int height;

        public Person(String name, int height) {
            this.name = name;
            this.height = height;
        }

        public Person() {
        }

        static enum Gender{
            MAN, WOMAN
        }

        @Getter
        @Setter
        public static class Man extends Person{
            private Gender gender;



            public Man(String name, int height, Gender gender) {
                super(name,height);
                this.gender = gender;
            }

            public Man() {
            }
        }

        @Getter
        @Setter
        public static class Woman extends Person{
            private Gender gender;

            public Woman(String name, int height, Gender gender) {
                super(name,height);
                this.gender = gender;
            }

            public Woman() {
            }
        }
    }
}
