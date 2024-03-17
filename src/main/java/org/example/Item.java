package org.example;

public class Item {
    private String name;
    private String shortName;
    private int maxCarry = 40000;
    private int weight;

    public Item(String name, String shortName, int weight) {
        this.name = name;
        this.shortName = shortName;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getMaxCarry() {
        return maxCarry;
    }

    public int getWeight() {
        return weight;
    }
}