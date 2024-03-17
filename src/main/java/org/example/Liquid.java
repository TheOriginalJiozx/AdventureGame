package org.example;

public class Liquid extends Consumable {
    private int healthPoints;

    public Liquid(String name, int healthPoints, int weight) {
        super(name, weight);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}