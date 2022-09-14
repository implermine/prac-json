package org.example.domain.jsonignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;


/**
 * 기본생성자가 없음
 */
@Getter
@Builder(builderClassName = "POJOBuilder")
@JsonDeserialize(builder = Book2.POJOBuilder.class)
@AllArgsConstructor
@ToString
public class Book2 {

    private String name;

    @JsonIgnore
    private Integer isbn;

    @JsonPOJOBuilder(withPrefix = "")
    public static class POJOBuilder{

    }


}
