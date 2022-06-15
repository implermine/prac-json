package org.example;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnnotationTest2{

    /**
     * @JsonGetter
     *
     * 이거 이용해서 객체 필드가 Boolean이나 boolean인 상황을 해결해보자.
     */

    @AllArgsConstructor
    private static class Person{
        private String name;
        private int height;
        private boolean isTall;
        private boolean isFun;
        private boolean isPerson;
        private Boolean isMan;

        public String getName() {
            return this.name;
        }

        public int getsdeight() {
            return this.height;
        }

//        public boolean isisTall() { // -> primitive boolean의 경우 그냥 isTall이라 생성된다. (롬복)
//            return this.isTall;
//        }

        public boolean isFun(){ // -> primitive boolean의 경우 그냥 isFun이라 생성 (롬복)
            return this.isFun;
        }

        public boolean isisPerson(){
            return this.isPerson;
        }

        public Boolean getIsMan() { // -> wrapper boolean의 경우 get * 이라고 표준 게터가 생성된다. (롬복)
            return this.isMan;
        }

        @JsonGetter("isTall") // -> dlw
        public boolean some(){
            return this.isTall;
        }
    }

    @Test
    @DisplayName("Boolean 값 직렬화 관련")
    public void booleanTest() throws JsonProcessingException {
        Person person = new Person("implermine" , 182, true,false,true ,true);

        String result = new ObjectMapper().writeValueAsString(person);

        System.out.println(result);
        //{"name":"implermine","isPerson":true,"isMan":true,"sdeight":182,"fun":false,"isTall":true}

        /**
         * 보다싶이, isTall로 지정해놓은 필드가 tall로 직렬화 되었다.
         * 즉, 메서드 isTall을 사용하였으며, 필드명과 상관없이 게터 메서드 이름의 is * 의 *를 카멜케이스라 생각하고
         * 첫 글자를 소문자로 바꿔 직렬화 하였다. (필드명과 상관없음 !! 게터명과 상관있음 (확인해봄))
         * 따라서, 게터 이름을 isisTall로 바꿔주면 정상작동할것이라 예상할 수 있다.
         * ! 게터 이름 막 바꾸면 그걸로 반환함 따라서, return this.필드가 본체고
         * getsdeight에다가 return this.height 하면 sdeight:182 반환함
         *
         */
    }
}
