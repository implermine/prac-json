package org.example.domain.hierarchy;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.ToString;

import java.util.List;

@ToString
public class Fish {

    @JsonSetter(nulls = Nulls.AS_EMPTY)
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
        System.out.println("Fish : setStringList() called...");
        this.stringList = stringList;
    }

    public String getHey() {
        return hey;
    }

    public void setHey(String hey) {
        this.hey = hey;
    }
}
