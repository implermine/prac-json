package org.example.domain.hierarchy;

import lombok.ToString;

import java.util.List;

@ToString
public class Fish {

    private List<String> stringList;

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
}
