package org.example;

public class NPC extends Enemy {
    public NPC(String name, int health, int damage, boolean isFriendly) {
        super(name, health, damage, isFriendly);
    }

    // Method to take damage
    public void takeDamage(int amount) {
        super.takeDamage(amount);
    }

    // Method to check if the NPC is defeated
    public boolean isDefeated() {
        return super.isDefeated();
    }
}