package org.example.annotation.polymorphism.model;

public class Cat extends Animal{

    boolean likesCream;
    public int lives;

    public Cat(){}

    public Cat(final String name){
        this.name = name;
    }
}
