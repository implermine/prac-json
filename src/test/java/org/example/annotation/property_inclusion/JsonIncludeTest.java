package org.example.annotation.property_inclusion;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * @JsonInclude
 *
 * empty / null / default value인 property를 exclude 배제 할 수 있습니다.
 *
 * Annotation used to indicate when value of the annotated property
 * (when used for a field, method or constructor parameter),
 * or all properties of the annotated class, is to be ``` serialized. ```
 * -> 즉, 직렬화 feature에만 적용된다라는 의미.
 */
public class JsonIncludeTest {


    @Setter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ToString
    public static class MyBean{
        public int id;
        public String name;

        public MyBean() {
            this.id = 7;
            this.name = "default name";
        }
    }

    /**
     * 직렬화 시 , null-value는 직렬화 미포함
     */
    @Test
    public void test1() throws JsonProcessingException {
        MyBean bean = new MyBean(1, null);

        // 직렬화
        String result = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(bean);

        System.out.println(result);
        /**
         * {
         *   "id" : 1
         * }
         */

    }

    /**
     * 역직렬화 시, null-value를 어떻게 처리하나?
     *
     * 난 바본가? -> 아닌것 같은데?
     *
     * 기본 생성자에 Default Name을 넣어두면 jsonString1의 경우도 역직렬화 시, null로 set되는것이 아니라,
     *
     * default name으로 set 될 줄 알았는데, 그렇지 않다.
     */
    @Test
    public void test2() throws JsonProcessingException{

        String jsonString1= "{\"id\": 2, \"name\": \"null\"}";
        String jsonString2 ="{\"id\": 2}";

        MyBean o1 = new ObjectMapper().readerFor(MyBean.class).readValue(jsonString1);
        MyBean o2 = new ObjectMapper().readerFor(MyBean.class).readValue(jsonString2);

        System.out.println(o1);
        /**
         * JsonIncludeTest.MyBean(id=2, name=null)
         */
        System.out.println(o2);
        /**
         * JsonIncludeTest.MyBean(id=2, name=null)
         */
    }

}
