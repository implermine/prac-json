package org.example.annotation.polymorphism.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "imNotType"
) // 직렬화-역직렬화시 `실제` 타입을 지칭하기 위함, 즉 DTYPE 설정 어노테이션
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = Dog.class, name = "doggy"),
                @JsonSubTypes.Type(value = Cat.class, name = "catty")
        }
) // @JsonTypeInfo와 같이 쓰임, 직렬화시 DTYPE에 어떤것을 기록할지 결정, 역직렬화시 무엇을 보고 역직렬화 할지 결정
@ToString
public class Animal {

    public String name;

    public Animal(){}

    public Animal(final String name){
        this.name = name;
    }
}
