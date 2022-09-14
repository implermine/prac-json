package org.example.domain.jsonignore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * JsonIgnore를 jackson에서 추천한대로 jsonProperty.access()로 변경
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Book3 {

    // 역직렬화 only, 직렬화에 ignore
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    // 직렬화 only , 역직렬화에 ignore
    @JsonProperty(value = "isbn", access = JsonProperty.Access.READ_ONLY)
    private Integer isbn;
}
