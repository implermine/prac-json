package org.example.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car {

//    @JsonProperty("color")
    private String color;
//    @JsonProperty("type")
    private String type;

    private int primitiveInt;
    private Integer wrapperInt;


    // jackson은 기본생성자를 이용해 unmarshalling을 시도합니다.
    // 이때 Reflection API를 사용해, 주어진 직렬데이터가 color : "something" 일때 color를 기준으로 Field를 탐색합니다.
    // 그러나 기본생성자가 없다면 안됩니다.

    // 기본 생성자를 만들거나 여타 다른 Constructor에 @JsonProperty("field_name")을 알려주어, Default 생성자가 아닌 다른 생성자로 unmarshalling을 시도하게끔 유도할 수도 있습니다.
    public Car(){

    }
//    public Car(@JsonProperty("color") String color, @JsonProperty("type") String type) {
//        this.color = color;
//        this.type = type;
//    }


    public Car(String color, String type) {
        this.color = color;
        this.type = type;
    }
}
