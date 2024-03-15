package org.example;

public class Weapon {
    private String name;
    private String shortName;
    private int damage;

    public Weapon(String name, String shortName, int damage) {
        this.name = name;
        this.shortName = shortName;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int damage() {
        return damage;
    }
}