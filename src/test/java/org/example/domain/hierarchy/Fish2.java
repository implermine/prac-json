package org.example.domain.hierarchy;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonDeserialize(builder = Fish2.POJOBuilder.class)
@Builder(builderClassName = "POJOBuilder")
@Getter
public class Fish2 {


    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> stringList;

    private String hey;
}
