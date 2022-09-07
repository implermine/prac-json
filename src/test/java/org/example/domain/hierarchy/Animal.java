package org.example.domain.hierarchy;

import lombok.ToString;

@ToString
public class Animal {

    private Fish fish;

    public Animal() {
        System.out.println("Animal : No-arg-constructor called...");
    }

    public Fish getFish() {
        System.out.println("Animal : getFish() called...");
        return this.fish;
    }

    public void setFish(Fish fish) {
        System.out.println("Animal : setFish() called...");
        this.fish = fish;
    }
}
