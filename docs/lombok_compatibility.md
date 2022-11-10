# 2022-09-08 Error Log

> 역직렬화 창구가 없는데, 임의로 역직렬화가 발생하던 문제


다음 클래스를 보자

```java

@Getter
@Builder(builderClassName = "POJOBuilder")
@JsonDeserialize(builder = Fish2.POJOBuilder.class)
public class Fish2 {


    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> stringList;

    private String hey;

}

```

위 클래스는 사실 stereotype과는 좀 다른데,
@JsonDeserialize(builder=***)는

baeldung에선 @JsonPOJOBuilder와 같이 쓰게끔 되어있다.

하지만, JsonPOJOBuilder에서 jackson이 default로 사용하는

빌더의 수정자 메서드 prefix는 `with-` 이므로

lombok (@Builder(~~~)) 이 만들어주는 빌더로는 사용할 수 없다.

따라서 관습적으로 다음과 같이 withPrefix를 만들어준다.

```java

@Getter
@Builder(builderClassName = "POJOBuilder")
@JsonDeserialize(builder = Fish2.POJOBuilder.class)
public class Fish2 {


    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> stringList;

    private String hey;

    @JsonPOJOBuilder(withPrefix = "")
    public static class POJOBuilder {

    }
}
```

이런식으로 만들어두면, Lombok이 POJOBuilder라는 이름이 이미 있으므로,

해당 POJOBuilder의 withPrefix 어노테이션을 살리면서 빌더를 만들어준다.

그렇지만, 이번 경우는 관습을 따르지 않은 형태였고, 문제가 발생하는 형태의

디롬복 형태는 다음과 같다.

```java

@JsonDeserialize(builder = Fish2.POJOBuilder.class)
public class Fish2 {


    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> stringList;

    private String hey;

    Fish2(List<String> stringList, String hey) {
        this.stringList = stringList;
        this.hey = hey;
    }

    public static POJOBuilder builder() {
        return new POJOBuilder();
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    public String getHey() {
        return this.hey;
    }

    public static class POJOBuilder {
        private List<String> stringList;
        private String hey;

        POJOBuilder() {
        }

        public POJOBuilder stringList(List<String> stringList) {
            this.stringList = stringList;
            return this;
        }

        public POJOBuilder hey(String hey) {
            this.hey = hey;
            return this;
        }

        public Fish2 build() {
            return new Fish2(stringList, hey);
        }

        public String toString() {
            return "Fish2.POJOBuilder(stringList=" + this.stringList + ", hey=" + this.hey + ")";
        }
    }
}

```

위와 같은 형태에선 `with-` 로 시작하는 builer 수정자 메서드가 없으므로,
@JsonDeserialize에 의해 jackson은 역직렬화를 수행할 때, builder를 사용하긴 합니다.
하지만, `with-` 가 없으므로 바로 `build()` 메서드로 제어권이 넘어가게 되고,
아무것도 초기화 되지 않은 채, 역직렬화가 끝나는것이 맞습니다.

그러나, 문제가 되는 점은 @JsonSetter(nulls = Nulls.AS_EMPTY) 입니다.

요게 필드에 붙어있는 대신, 이 경우는 No-arg -> Setter로 이어지는 역직렬화 과정이 아니기 때문에,
의도처럼 null 값이 넘어오더라도 Empty List를 반환하지 않아야 합니다.

그러나, 문제상황에선 다음과 같이 두번의 문제가 발생합니다.

* null 값이 넘어오면 제대로 [] Empty List를 반환하며
* null 값이 아닌값이 넘어오더라도 제대로 매핑해줌

첫번째의 이유는, Delombok이 완전하지 않기 때문입니다.
컴파일된 클래스파일을 열어서 Decompile을 해보면, JsonSetter가 필드가 아닌 빌더에도 붙어있는것을
확인할 수 있습니다.

```java
@JsonSetter(nulls = Nulls.AS_EMPTY)
public POJOBuilder stringList(List<String> stringList){
    this.stringList=stringList;
    return this;
}
```

따라서, 이는 intellij가 delombok을 제대로 수행해주지 않은 문제입니다.
(따라서, 디롬복했을때와 롬복을 그대로 뒀을때의 컴파일 결과가 다릅니다.)
두번째 문제의 이유는 @JsonSetter에 있습니다.

@JsonSetter는 역직렬화 수행의 책임을 누가 질 것인지를 담당합니다.

따라서, @JsonSetter가 붙게된 빌더의 `with-` prefix가 존재하지 않는 메서드는
앞으로 stringList의 역직렬화를 담당하게 됩니다.

이러한 사유로 stringList는 매핑이 잘 되지만, hey는 매핑이 되지 않는 것입니다.

