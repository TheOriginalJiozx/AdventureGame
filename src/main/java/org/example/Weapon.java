package org.example;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, String shortName, int damage) {
        super(name, shortName);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}