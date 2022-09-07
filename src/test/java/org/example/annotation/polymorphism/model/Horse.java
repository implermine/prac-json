package org.example.annotation.polymorphism.model;

public class Horse extends Animal{
    public int horsePower;
    public boolean isAlive;

    public Horse(){}

    public Horse(String name, int horsePower, boolean isAlive) {
        super(name);
        this.horsePower = horsePower;
        this.isAlive = isAlive;
    }
}
