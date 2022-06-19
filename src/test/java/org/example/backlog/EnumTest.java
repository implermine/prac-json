package org.example.backlog;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.ToString;
import org.junit.jupiter.api.Test;

public class EnumTest {

    // 예시 객체
    @ToString
    private static class MyBean{
        private String name;
        private int height;
        private Job job;

        public enum Job{
            Dentist,
            Doctor,
            Developer,
            Teacher
        }

        public MyBean() {
        }

        // Constructor
        public MyBean(String name, int height, Job job) {
            this.name = name;
            this.height = height;
            this.job = job;
        }

        // Getters
        public String getName() {
            return name;
        }

        public int getHeight() {
            return height;
        }

        public Job getJob() {
            return job;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public void setJob(Job job) {
            this.job = job;
        }
    }

    // Serialization Test
    @Test
    public void enum_serialize() throws JsonProcessingException {
        MyBean bean = new MyBean("myName", 170, MyBean.Job.Dentist);

        String s = new ObjectMapper().writeValueAsString(bean);

        System.out.println(s);
        // EnumTest.MyBean(name=myName, height=170, job=Dentist)
    }

    // Deserialization Test
    @Test
    public void enum_deserialize() throws JsonProcessingException{
     String jsonString = "{\"name\":\"myName\",\"height\":170,\"job\":\"Dentist\"}";
     // job을 Dentist란 String으로 정의했다.

        MyBean bean = new ObjectMapper().readValue(jsonString, MyBean.class);

        System.out.println(bean);
        // EnumTest.MyBean(name=myName, height=170, job=Dentist)

    }


}
