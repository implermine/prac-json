package org.example.domain.hierarchy;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.ToString;

import java.util.List;

@ToString
public class Fish {

    @JsonSetter(nulls = Nulls.AS_EMPTY) // setter 타기전에 이미 Serialized Json에서 Empty List로 setter에 들고오네
    private List<String> stringList;
    private String hey;

    public Fish() {
        System.out.println("Fish : No-arg-constructor called...");
    }

    public List<String> getStringList() {
        System.out.println("Fish : getStringList() called...");
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        System.out.println("Fish : setStringList() called..." + " parameter stringList has been already initialized as Empty ArrayList... ");
        this.stringList = stringList;
    }

    public String getHey() {
        return hey;
    }

    public void setHey(String hey) {
        this.hey = hey;
    }
}
