package org.example.domain.hierarchy;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Animal2.POJOBuilder.class)
public class Animal2 {

    private Fish2 fish;

    public Animal2() {
        System.out.println("Animal : No-arg-constructor called...");
    }

    public Animal2(Fish2 fish){
        System.out.println("Animal : All-arg-constructor called...");
        this.fish = fish;
    }

    public static POJOBuilder builder() {
        return new POJOBuilder();
    }

    public Fish2 getFish() {
        System.out.println("Animal : getFish() called...");
        return this.fish;
    }

    public void setFish(Fish2 fish) {
        System.out.println("Animal : setFish() called...");
        this.fish = fish;
    }

    public String toString() {
        return "Animal2(fish=" + this.getFish() + ")";
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class POJOBuilder{

        private Fish2 fish;

        POJOBuilder() {
        }

        public POJOBuilder fish(Fish2 fish) {
            System.out.println("Animal2 builder에서 builder.fish( param ) 으로 fish 세팅");
            this.fish = fish;
            return this;
        }

        public Animal2 build() {
            return new Animal2(fish);
        }

        public String toString() {
            return "Animal2.POJOBuilder(fish=" + this.fish + ")";
        }
    }
}
