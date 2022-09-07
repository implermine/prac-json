package org.example.domain.HasXYZ.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Dolphin {

    private String name;
    private boolean isAlive;
    private Boolean tall;
    private boolean talent;

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        System.out.println("isAlive used...");
        return isAlive;
    }

    public Boolean getTall() {
        return tall;
    }

    public boolean hasTalent(){
        System.out.println("hasTalent used...");
        return talent;
    }
}
