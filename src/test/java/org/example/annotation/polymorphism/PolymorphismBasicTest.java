package org.example.annotation.polymorphism;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Next let's take a look at Jackson polymorphic type handling annotations:
 *
 * @JsonTypeInfo – indicates details of what type information to include in serialization
 * @JsonSubTypes – indicates sub-types of the annotated type
 * @JsonTypeName – defines a logical type name to use for annotated class
 * Let's examine a more complex example, and use all three – @JsonTypeInfo, @JsonSubTypes, and @JsonTypeName – to serialize/deserialize the entity Zoo:
 */
public class PolymorphismBasicTest {


    /**
     * 직렬화
     */
    @Test
    public void test1() throws JsonProcessingException {
        Zoo.Dog dog = new Zoo.Dog("lacy");
        Zoo zoo = new Zoo(dog);

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(zoo);

        System.out.println(jsonString);
        /**
         * {
         *   "animal" : {
         *     "type" : "dog",
         *     "name" : "lacy",
         *     "barkVolume" : 0.0
         *   }
         * }
         */
    }

    /**
     * 역직렬화
     */
    @Test
    public void test2() throws JsonProcessingException {

        /**
         * {
         *     "animal":{
         *         "name":"lacy",
         *         "type":"cat"
         *     }
         * }
         */
        String jsonString = "{\"animal\":{\"name\":\"lacy\",\"type\":\"cat\"}}";

        Zoo zoo = new ObjectMapper().readerFor(Zoo.class).readValue(jsonString);

        Assertions.assertThat(zoo.animal.getClass()).isEqualTo(Zoo.Cat.class);


    }

    /**
     * baeldung에 나온것과 다른 JsonTypeInfo 사용해보기, java docs에서 예시 가져옴
     */
    @Test
    public void test3() throws JsonProcessingException {
        Zoo2.Dog dog = new Zoo2.Dog("lacy");
        Zoo2 zoo = new Zoo2(dog);

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(zoo);

        System.out.println(jsonString);

        /**
         * {
         *   "animal" : {
         *     "class" : "org.example.annotation.polymorphism.PolymorphismBasicTest$Zoo2$Dog",
         *     "name" : "lacy",
         *     "barkVolume" : 0.0
         *   }
         * }
         */


    }

    /**
     * baeldung에 나온것과 다른 JsonTypeInfo 사용해보기, java docs에서 예시 가져옴 마찬가지 zoo3이용
     */
    @Test
    public void test4() throws JsonProcessingException {
        Zoo3.Dog dog = new Zoo3.Dog("lacy");
        Zoo3 zoo = new Zoo3(dog);

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(zoo);

        System.out.println(jsonString);

        /**
         * {
         *   "animal" : {
         *     "dog" : {
         *       "name" : "lacy",
         *       "barkVolume" : 0.0
         *     }
         *   }
         * }
         */
    }

    /**
     * JsonSubTypes로 등록해놓지 않는다면?
     */
    @Test
    public void test5() throws JsonProcessingException{
        Zoo.Horse horse = new Zoo.Horse("gold",100,true);
        Zoo zoo = new Zoo(horse);

        String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(zoo);

        System.out.println(jsonString);

        /**
         * {
         *   "animal" : {
         *     "type" : "horse",
         *     "name" : "gold",
         *     "horsePower" : 100,
         *     "isAlive" : true
         *   }
         * }
         */
    }


    // =================================================================================================================
    public static class Zoo {
        public Animal animal;

        public Zoo() {

        }

        public Zoo(final Animal animal) {
            this.animal = animal;
        }

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type") // --> 여기 use = JsonTypeInfo.Id.NONE 하면 직렬화 시에 type 안내려줌
        @JsonSubTypes({
                @JsonSubTypes.Type(value = Dog.class, name = "dog"),
                @JsonSubTypes.Type(value = Cat.class, name = "cat")
        }) // ---> @JsonSubTypes는 지우면 test2에서 fail난다. 즉 역직렬화와 연관이 있음.
        public static class Animal {
            public String name;

            public Animal() {
            }

            public Animal(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("dog") // <- 여기 붙여도 좋고 위에 @JsonSubTypes쪽에 붙여도 됌. 둘중 하나만 붙여도 효과 있으며, 붙이지 않으면 package네임을 붙여서 길게 표현함.
        public static class Dog extends Animal {
            public double barkVolume;

            public Dog() {
            }

            public Dog(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("cat")
        public static class Cat extends Animal {
            boolean likesCream;
            public int lives;

            public Cat() {
            }

            public Cat(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("horse")
        public static class Horse extends Animal {
            public int horsePower;
            public boolean isAlive;

            public Horse() {

            }

            public Horse(final String name , final int horsePower, final boolean isAlive) {
                super(name);
                this.horsePower = horsePower;
                this.isAlive = isAlive;
            }
        }
    }

    // ================================================================================================================

    /**
     * zoo1과 달리 Animal에 달린 어노테이션이 다르다.
     */
    public static class Zoo2 {
        public Zoo2.Animal animal;

        public Zoo2() {

        }

        public Zoo2(final Zoo2.Animal animal) {
            this.animal = animal;
        }

        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
        public static class Animal {
            public String name;

            public Animal() {
            }

            public Animal(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("dog") // <- 여기 붙여도 좋고 위에 @JsonSubTypes쪽에 붙여도 됌. 둘중 하나만 붙여도 효과 있으며, 붙이지 않으면 package네임을 붙여서 길게 표현함.
        public static class Dog extends Zoo2.Animal {
            public double barkVolume;

            public Dog() {
            }

            public Dog(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("cat")
        public static class Cat extends Zoo2.Animal {
            boolean likesCream;
            public int lives;

            public Cat() {
            }

            public Cat(final String name) {
                this.name = name;
            }
        }

        public static class Horse extends Zoo2.Animal {
            private int horsePower;
            private boolean isAlive;

            public Horse() {

            }

            public Horse(final int horsePower, final boolean isAlive) {
                this.horsePower = horsePower;
                this.isAlive = isAlive;
            }
        }
    }

    //=================================================================================================================

    /**
     * 이 어노테이션들을 사용
     *
     * @JsonTypeInfo(use=Id.NAME, include=As.WRAPPER_OBJECT)
     * @JsonSubTypes({com.myemp.Impl1.class, com.myempl.Impl2.class})
     */
    public static class Zoo3 {
        public Zoo3.Animal animal;

        public Zoo3() {

        }

        public Zoo3(final Zoo3.Animal animal) {
            this.animal = animal;
        }

        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
        @JsonSubTypes({
                @JsonSubTypes.Type(value = Zoo3.Dog.class, name = "dog"),
                @JsonSubTypes.Type(value = Zoo3.Cat.class, name = "cat")
        })
        public static class Animal {
            public String name;

            public Animal() {
            }

            public Animal(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("dog") // <- 여기 붙여도 좋고 위에 @JsonSubTypes쪽에 붙여도 됌. 둘중 하나만 붙여도 효과 있으며, 붙이지 않으면 package네임을 붙여서 길게 표현함.
        public static class Dog extends Zoo3.Animal {
            public double barkVolume;

            public Dog() {
            }

            public Dog(final String name) {
                this.name = name;
            }
        }

        @JsonTypeName("cat")
        public static class Cat extends Zoo3.Animal {
            boolean likesCream;
            public int lives;

            public Cat() {
            }

            public Cat(final String name) {
                this.name = name;
            }
        }

        public static class Horse extends Zoo3.Animal {
            private int horsePower;
            private boolean isAlive;

            public Horse() {

            }

            public Horse(final int horsePower, final boolean isAlive) {
                this.horsePower = horsePower;
                this.isAlive = isAlive;
            }
        }
    }
}
