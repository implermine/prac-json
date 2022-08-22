# Ref

fasterxml.jackson API를 사용합니다.   
하는김에 lombok @superBuilder나 @EqualAndHashCode도 여기서 좀 살펴보려고 합니다.

기반 레퍼런스 문서 : [https://www.baeldung.com/jackson]()   
위 문서 Source : [https://github.com/eugenp/tutorials/tree/master/jackson-simple]()

# Summary

* 직렬화 기본 전략: Public Access -> Getter
* 역직렬화 기본 전략 : NoArg -> Public Access -> Setter


### Default 역직렬화 전략
#### 역직렬화 (String(JSON) -> JAVA Object)
1. String에는 값이 있고, JAVA Object에 해당 필드가 없다면 오류 발생.
2. String에는 값이 없거나 null이고, JAVA Object에 해당 필드가 있다면 null을 대응. (이때, JAVA Object의 필드가 primitive type이라면 해당 primitive type의 적절한 항등원을 대입 (e.g. int -> 0))

## 작업 로그
2022-06-27 롬복 관련작업까지 완
2022-08-22 JsonNode로 Temporary Response Dto 계획