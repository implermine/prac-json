package org.example.domain.hierarchy;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

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
