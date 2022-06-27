# Ref

fasterxml.jackson API를 사용합니다.   
하는김에 lombok @superBuilder나 @EqualAndHashCode도 여기서 좀 살펴보려고 합니다.

기반 레퍼런스 문서 : [https://www.baeldung.com/jackson]()   
위 문서 Source : [https://github.com/eugenp/tutorials/tree/master/jackson-simple]()

# Summary

* ObjectMapper의 Default 전략은 JSON 객체에는 값이 있고, Java 객체에는 없다면 unmarshalling 시 Exception이 발생한다.

* ObjectMapper의 Default 전략은 JSON 객체에는 값이 없거나 null이고, Java 객체에는 있다면 null을 대응한다. 이때 primitiveType의 경우 0이 대입된다 (int의 경우)
  이러한 default 전략을 configure를 통해 바꿀 수 있다.

## 작업 로그
2022-06-27 롬복 관련작업까지 완