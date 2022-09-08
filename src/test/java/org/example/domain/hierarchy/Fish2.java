package org.example.domain.hierarchy;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.ToString;

import java.util.List;

@ToString
@JsonDeserialize(builder = Fish2.Fish2Builder.class)
public class Fish2 {


    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<String> stringList;

    public Fish2() {
        System.out.println("Fish : No-arg-constructor called...");
    }

    public Fish2(List<String> stringList) {
        System.out.println("Fish : All-arg-constructor called...");
        this.stringList = stringList;
    }

    public static Fish2Builder builder() {
        return new Fish2Builder();
    }

    public List<String> getStringList() {
        System.out.println("Fish : getStringList() called...");
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        System.out.println("Fish : setStringList() called...");
        this.stringList = stringList;
    }

    public static class Fish2Builder {
        private List<String> stringList;

        Fish2Builder() {
        }

        public Fish2Builder stringList(List<String> stringList) {
            this.stringList = stringList;
            return this;
        }

        public Fish2 build() {
            return new Fish2(stringList);
        }

        public String toString() {
            return "Fish2.Fish2Builder(stringList=" + this.stringList + ")";
        }
    }
}
