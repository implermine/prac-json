package org.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Person {

    String name;
    int height;
    Job job;

    public enum Job{
        Dentist,
        Doctor,
        Developer,
        Teacher
    }
}
