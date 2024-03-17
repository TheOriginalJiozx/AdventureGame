package org.example;

public class Food extends Consumable {
    private int healthPoints;

    public Food(String name, int healthPoints, int weight) {
        super(name, weight);
        this.healthPoints = healthPoints;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}