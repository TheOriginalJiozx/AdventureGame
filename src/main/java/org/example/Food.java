package org.example;

public class Food extends Item {
    private int healthPoints;

    public Food(String name, String shortName, int healthPoints) {
        super(name, shortName);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}