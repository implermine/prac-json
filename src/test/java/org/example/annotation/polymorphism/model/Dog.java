package org.example.annotation.polymorphism.model;

public class Dog extends Animal{
    public double barkVolume;

    public Dog(){}

    public Dog(final String name){
        this.name = name;
    }
}
