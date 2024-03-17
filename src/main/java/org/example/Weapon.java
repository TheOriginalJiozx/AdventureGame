package org.example;

public class Weapon extends Item {
    private int damage;
    private boolean equipped;

    public Weapon(String name, int damage, int weight) {
        super(name, weight);
        this.damage = damage;
        this.equipped = false;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void equip() {
        equipped = true;
    }

    public void unequip() {
        equipped = false;
    }
}