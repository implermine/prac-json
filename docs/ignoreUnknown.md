## @JsonIgnoreProperties(ignoreUnknown=true)

해당 설정은 Chaotic, Json, Free, Serialized, NoSQL 같은 자유로운 데이터 셋에서 `모르는` 데이터를 어떻게 관리할 지에 대한 설정이다.

역직렬화를 수행할 때, 해당 역직렬화 될 클래스의 매칭되는 필드가 없으면 `그` 자유로운 데이터셋은 어떻게 할 지에 대한 이야기이다.

Default Value가 false인데, 즉, 모르는 데이터가 존재하면 무시하지 않겠다는 것이다.

이러한 세팅은 주로 에러를 야기하기에, true로 바꿔서 사용하는 경우가 많은데,

이 경우에 Jackson이 Default를 true가 아닌 false로 설정한 이유를 짐작 해 보는것이 좋을 수 있다.

> `모르는` 데이터가 존재하는것은 Fully Checked 되지 않았다는 의미로, 미인가된 데이터가 외부로 노출 될 가능성을 시사한다.