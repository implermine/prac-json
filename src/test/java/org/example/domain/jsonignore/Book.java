package org.example.domain.jsonignore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book {

    private String name;

    @JsonIgnore
    private Integer isbn;

}
