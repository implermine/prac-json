package org.example;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

public class AnnotationTest {

    //@JsonAnyGetter
    //@JsonAnyGetter annotation allows for the flexibility of using a Map field as standard properties
    //@JsonAnyGetter annotation은 Map을 property로 갖는 자바 빈에 대해서 flexibility를 부여합니다.
    // For example, the ExtendableBean entity has the name property and a set of extendable attributes in the form of key/value pairs
    // 예를들어, 아래의 ExntendableBean 객체는  key/value pair의 형태를 갖는 객체를 갖고 있습니다.

    public static class ExtendableBean {
        public String name;
        private Map<String, String> properties;

        public ExtendableBean() {
            this.properties = new HashMap<>();
        }

        public ExtendableBean(String name) {
            this.name = name;
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

    @Test
    @DisplayName("@JsonAnyGetter")
    // 별거없음, Json Serialization은 Getter를 통해 이루어지는데, 이때, key/value pair인 property를 어떻게 보여줄지 결정함
    public void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {

        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");

        String result = new ObjectMapper().writeValueAsString(bean);

        System.out.println(result);
        //JsonAnyGetter가 없을 경우 -> {"name":"My bean","properties":{"attr2":"val2","attr1":"val1"}} -> depth가 그대로 wrapping됨
        //JsonAnyGetter가 있을 경우 -> {"name":"My bean","attr2":"val2","attr1":"val1"} -> depth가 unwrap됨.


        assertThat(result).contains("attr1");
        assertThat(result).contains("val1");
    }

    // ============================================================================================================

    // [중요]
    // @JsonGetter

    // ============================================================================================================

    //The @JsonGetter annotation is an alternative to the @JsonProperty annotation, which marks a method as a getter method.
    // @JsonGetter annotation 은 @JsonProperty의 대체제입니다. 게터 메서드를 지정해주는 역할을 합니다
    // 여기서 게터 메서드를 지정함은, Json Serializing을 시도할 때, Java Bean 규약에 맞는 Getter 메서드명을 직렬화 중에 사용하는데,
    // 이때 default 게터명을 사용하지 않고자 할때 사용할 수 있습니다.

    public static class MyBean {
        public int id;
        private String name;

        public MyBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonGetter("name")
        public String getTheCustomizedGetter() {
            return "i'm customized";
        }

        public String getName() {
            return this.name;
        }
    }

    @Test
    public void whenSerializingUsingJsonGetter_thenCorrect() throws JsonProcessingException {

        MyBean bean = new MyBean(1, "My Bean");

        String result = new ObjectMapper().writeValueAsString(bean);

        System.out.println(result);
        // {"id":1,"name":"i'm customized"}
        assertThat(result).doesNotContain("My Bean");
    }

    // @JsonPropertyOrder
    // We can use the @JsonPropertyOrder annotation to specifiy the order of properties on serialization
    // 직렬화 전략 시, 어떤 값이 먼저 나오게끔 설정 함 (그럴일이 있나..?)
    @JsonPropertyOrder({"name"})
    public static class MyBean2 {
        public int id;
        public String name;

        public MyBean2(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void whenSerializingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {

        MyBean2 bean = new MyBean2(1, "My bean");

        String result = new ObjectMapper().writeValueAsString(bean);

        System.out.println(result);
        // {"name":"My bean","id":1} <- name이 먼저나옴
    }

    // ============================================================================================================
    // @JsonRawValue : 해당 String에 Json 객체가 이미 직렬화 되어 삽입되어있는 경우, 이를 역직렬화 된 객체라 판단하고 직렬화를 수행하도록 함
    // The @JsonRawValue annotation can instruct Jackson to serialize a property exactly as is

    // 사용 예시 객체
    public static class JsonRawValueBean {
        private String name;

        @JsonRawValue
        private String json;

        public String getName() {
            return name;
        }

        @JsonRawValue // 메서드에 놓을 수 도 있다.
        public String getJson() {
            return json;
        }

        public JsonRawValueBean(String name, String json) {
            this.name = name;
            this.json = json;
        }
    }

    // 테스트
    @Test
    public void whenSerializingUsingJsonRawValue_thenCorrect()
            throws JsonProcessingException {

        JsonRawValueBean bean = new JsonRawValueBean("My bean", "{\"attr\":false}");

        String result = new ObjectMapper().writeValueAsString(bean);

        System.out.println(result);
        //{"name":"My bean","json":{"attr":false}}
        // 만약 @JsonRawValue 어노테이션이 없다면
        // {"name":"My bean","json":"{\"attr\":false}"}
    }

    // ============================================================================================================

    // @JsonValue

    // ============================================================================================================
    // 설명 :
    // @JsonValue indicates a single method that the library will use to serialize the entire instance.
    // 전체 객체를 하나의 게터로 함축한다.

    // 예시 객체 1
    public enum TypeEnumWithValue {
        TYPE1(1, "Type A"), TYPE2(2, "Type 2");

        private Integer id;
        private String name;

        // standard constructors
        TypeEnumWithValue(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }

    // 예시 객체 2
    public static class Foo1 {
        private String name;
        private String name2;

        @JsonValue
        private int height;

        public Foo1(String name, String name2, int height) {
            this.name = name;
            this.name2 = name2;
            this.height = height;
        }

        public String getName() {
            return name;
        }

        public String getName2() {
            return name2;
        }

        public int getHeight() {
            return height;
        }
    }

    // 테스트

    @Test
    public void whenSerializingUsingJsonValue_thenCorrect()
            throws JsonParseException, IOException {

        String enumAsString = new ObjectMapper()
                .writeValueAsString(TypeEnumWithValue.TYPE1);

        System.out.println(enumAsString);

    }

    // 테스트 2

    @Test
    public void test2() throws JsonProcessingException {
        Foo1 foo1 = new Foo1("name1", "name2", 170);

        String str = new ObjectMapper().writeValueAsString(foo1);

        System.out.println(str);
        // 170
    }

    // ============================================================================================================

    // @JsonRootName

    // ============================================================================================================

    /**
     * 설명 : Json 객체를 wrapping 합니다.
     * 원래 Person이라는 객체가 있어도 Serialization을 수행하면
     * Person이란 말은 없어지고 person의 필드만 직렬화되어 보여지는데
     * 이 감싸고 있는 애의 이름을 남깁니다.
     */
    @JsonRootName(value = "user")
    @AllArgsConstructor
    private static class UserWithRoot {
        public int id;
        public String name;

    }

    @AllArgsConstructor
    private static class UserWithCommon { // JsonRootName 없으면 밑처럼 enable 해도 의미 없음? (Test 2)
        public int id;
        public String name;
    }

    @Test
    public void whenSerializingUsingJsonRootName_thenCorrect() throws JsonProcessingException {
        UserWithRoot user = new UserWithRoot(1, "John");

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result = mapper.writeValueAsString(user);


        System.out.println(result);
        //{"user":{"id":1,"name":"John"}}
    }

    // JsonRootName 없으면 위처럼 enable 해도 의미 없음? (Test 2)
    // 이름 정하는거임 따라서, Wrapping은 mapper.enable(Serialization.WRAP_ROOT_VALUE가 함)
    @Test
    public void JsonRootName_test2() throws JsonProcessingException {
        UserWithCommon user = new UserWithCommon(1, "John");

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.WRAP_ROOT_VALUE);
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        String result = objectWriter.writeValueAsString(user);

        System.out.println(result);
        /**
         * {
         *   "UserWithCommon" : {
         *     "id" : 1,
         *     "name" : "John"
         *   }
         * }
         */
    }

    // ============================================================================================================

    // @JsonSerialize

    // ============================================================================================================

    // 직렬화를 어떻게 수행할 지 판단할 CustomSerializer를 등록 할 수 있음.

    // dto 내부에서 간단한 로직을 돌리려면 이것보다 직렬화 판단 로직인 JsonGetter를 이용하는것이 더 좋아보이고,
    // 해당 어노테이션인 @JsonSerializer를 사용할때는, 같은 로직을 반복하여 여러 필드에 사용해야 하는 경우 (e.g. Date format의 yyyy 의 위치를 바꿔야 한다)
    // 혹은 여타 다른 Service Layer로 데이터 운송이 필요하다 (Spring) 사용하면 좋을 것 같습니다.
    private static class EventWithSerializer {
        public String name;

        @JsonSerialize(using = CustomDateSerializer.class)
        public Date eventDate;

        public Date eventDateButNoAnnotation;

        public EventWithSerializer(){
            super();
        }

        public EventWithSerializer(final String name, final Date eventDate, final Date eventDateButNoAnnotation){
            this.name = name;
            this.eventDate = eventDate;
            this.eventDateButNoAnnotation = eventDateButNoAnnotation;
        }

        public Date getEventDate(){
            return this.eventDate;
        }

        public String getName(){
            return this.name;
        }

        public Date getEventDateButNoAnnotation(){
            return this.eventDateButNoAnnotation;
        }

    }

    private static class CustomDateSerializer extends StdSerializer<Date>{

        private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        public CustomDateSerializer(){
            this(null);
        }

        public CustomDateSerializer(Class<Date> t){
            super(t);
        }

        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(formatter.format(value));
            // SimpleDateFormat::format은 Date를 String으로
        }
    }

    @Test
    public void jsonSerializeTest() throws ParseException, JsonProcessingException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        String toParse = "20-12-2014 02:30:00";
        Date date = df.parse(toParse);
        EventWithSerializer event = new EventWithSerializer("party", date, date);

        String result = new ObjectMapper().writeValueAsString(event);
        String resultFormatted = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(event);

        System.out.println(resultFormatted);

        /**
         * {
         *   "name" : "party",
         *   "eventDate" : "2014-12-20 02:30:00", <- yyyy--MM-dd 이며 default serialization 전략인 epoch time을 사용치 않음.
         *   "eventDateButNoAnnotation" : 1419010200000 <- epoch time
         * }
         */
    }

}
