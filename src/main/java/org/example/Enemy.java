package org.example;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private List<String> vulnerableWeapons;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.vulnerableWeapons = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDefeated() {
        return health <= 0;
    }

    public void addVulnerableWeapon(String weaponName) {
        vulnerableWeapons.add(weaponName);
    }

    public boolean isVulnerableToWeapon(String weaponName) {
        return vulnerableWeapons.contains(weaponName);
    }
}