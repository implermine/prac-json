package org.example.annotation.mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * 역직렬화 수행 기본을 확인합니다.
 */

/**
 * 역직렬화 기본전략은 NoArgConstructor입니다.
 * 기본생성자가 존재하지 않는다면 역직렬화를 수행할 수 없습니다.
 */
public class DeserializationBasicTest {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void deserializationBasicTest() throws JsonProcessingException {
        String jsonString = "{\"name\":\"implermine\",\"height\":180}";

        try {
            BeanWithAllArgConstructor beanWithAllArgConstructor = objectMapper.readValue(jsonString, BeanWithAllArgConstructor.class);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        BeanWithNoArgConstructorAndSetter beanWithNoArgConstructorAndSetter = objectMapper.readValue(jsonString, BeanWithNoArgConstructorAndSetter.class);

        System.out.println(beanWithNoArgConstructorAndSetter);

        /**
         * BeanWithNoArgConstructorAndSetter :: NoArgConstructor
         * BeanWithNoArgConstructorAndSetter :: setName
         * BeanWithNoArgConstructorAndSetter :: setHeight
         * BeanWithNoArgConstructorAndSetter(name=implermine, height=180)
         */

        BeanWithNoArgConstructorAndPublicFields beanWithNoArgConstructorAndPublicFields = objectMapper.readValue(jsonString, BeanWithNoArgConstructorAndPublicFields.class);

        System.out.println(beanWithNoArgConstructorAndPublicFields);

        /**
         * BeanWithNoArgConstructorAndSetter :: NoArgConstructor
         * BeanWithNoArgConstructorAndPublicFields(name=implermine, height=180)
         */
    }
}

@ToString
class BeanWithAllArgConstructor {
    private String name;
    private int height;

    public BeanWithAllArgConstructor(String name, int height) {
        this.name = name;
        this.height = height;

        System.out.println("BeanWithAllArgConstructor :: AllArgConstructor");
    }
}

@ToString
class BeanWithNoArgConstructorAndSetter {
    private String name;
    private int height;

    public BeanWithNoArgConstructorAndSetter() {
        System.out.println("BeanWithNoArgConstructorAndSetter :: NoArgConstructor");

    }

    public void setName(String name) {
        this.name = name;
        System.out.println("BeanWithNoArgConstructorAndSetter :: setName");

    }

    public void setHeight(int height) {
        this.height = height;
        System.out.println("BeanWithNoArgConstructorAndSetter :: setHeight");

    }
}

@ToString
class BeanWithNoArgConstructorAndPublicFields {
    public String name;
    public int height;

    public BeanWithNoArgConstructorAndPublicFields() {
        System.out.println("BeanWithNoArgConstructorAndSetter :: NoArgConstructor");

    }
}




