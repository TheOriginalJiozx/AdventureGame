package org.example;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private List<String> vulnerableWeapons;
    private boolean isFriendly;

    public Enemy(String name, int health, int damage, boolean isFriendly) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.vulnerableWeapons = new ArrayList<>();
        this.isFriendly = isFriendly;
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

    public boolean isVulnerableToWeapon(String weaponName) {
        return vulnerableWeapons.contains(weaponName);
    }

    public boolean isFriendly() {
        return isFriendly;
    }

    public void setFriendly(boolean friendly) {
        isFriendly = friendly;
    }
}