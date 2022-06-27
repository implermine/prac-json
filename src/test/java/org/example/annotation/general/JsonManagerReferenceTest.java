package org.example.annotation.general;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @JsonManagedReference
 * @JsonBackReference
 *
 * can handle parent/child relationships and work around loops
 *
 * 순환참조를 해결한다.
 * 그럴일은 없겠지만 JPA를 사용할때 연관관계를 가진 객체를 직렬화 할 때 발생하는 잠재적인 문제를 해결한다.
 *
 * 양방향 연관관계가 존재할때만 사용하며,
 *
 * 주종관계는 상관없지만, 실제 JPA에선 주가 Many이며 자식인 것이 더 나을 것 같다. (연관관계 주인)
 */
public class JsonManagerReferenceTest {


    Member member1;
    Member member2;
    Team team;

    @BeforeEach
    public void beforeEach(){
        member1 = new Member("faker", new Info("페이커"));
        member2 = new Member("wolf" , new Info("울프"));
        List<Member> memberList = new ArrayList<>(Arrays.asList(member1,member2));
        team = new Team("KT", new Info("케이티"), memberList);
        member1.setTeam(team);
        member2.setTeam(team);
    }

    @Test
    public void test() throws JsonProcessingException {

        // given
        // - beforeEach
        ObjectMapper objectMapper = new ObjectMapper();


        // when
        String s1 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(member1);
        String s2 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(member2);
        String s3 = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(team);

        System.out.println(s1);
        System.out.println("========================================================================================");
        System.out.println(s2);
        System.out.println("========================================================================================");
        System.out.println(s3);

        /**
         * 
         * {
         *   "name" : "faker",
         *   "info" : {
         *     "nickName" : "페이커"
         *   }
         * }
         * ========================================================================================
         * {
         *   "name" : "wolf",
         *   "info" : {
         *     "nickName" : "울프"
         *   }
         * }
         * ========================================================================================
         * {
         *   "name" : "KT",
         *   "info" : {
         *     "nickName" : "케이티"
         *   },
         *   "memberList" : [ {
         *     "name" : "faker",
         *     "info" : {
         *       "nickName" : "페이커"
         *     }
         *   }, {
         *     "name" : "wolf",
         *     "info" : {
         *       "nickName" : "울프"
         *     }
         *   } ]
         * }
         *
         */



    }


    // =================================================================================================================

    // 예시 객체

    @Setter
    @Getter
    private static class Team{
        private String name;
        private Info info;

        @JsonManagedReference
        private List<Member> memberList;

        public Team(String name, Info info, List<Member> memberList) {
            this.name = name;
            this.info = info;
            this.memberList = memberList;
        }
    }

    @Setter
    @Getter
    private static class Member{
        private String name;
        private Info info;

        @JsonBackReference
        private Team team;

        public Member(String name, Info info) {
            this.name = name;
            this.info = info;
        }
    }
    @Getter
    @AllArgsConstructor
    private static class Info{
        private String nickName;
    }


}
