package org.example;

public class Food extends Item {
    private int healthPoints;

    public Food(String name, String shortName, int healthPoints, int weight) {
        super(name, shortName, weight);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}