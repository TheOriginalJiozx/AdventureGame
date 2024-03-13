package org.example;

public class Food implements ItemOrFood {
    private String name;
    private String shortName;
    private int healthPoints;

    public Food(String name, String shortName, int healthPoints) {
        this.name = name;
        this.shortName = shortName;
        this.healthPoints = healthPoints;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}