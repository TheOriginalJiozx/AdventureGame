package org.example;

public class RangedWeapon extends Weapon {
    private int ammunition;
    public static final int INFINITE_AMMO_CAPACITY = Integer.MAX_VALUE;

    public RangedWeapon(String name, String shortName, int damage, int ammunition, int weight) {
        super(name, shortName, damage, weight);
        this.ammunition = ammunition;
    }

    public int getAmmonition() {
        return ammunition;
    }

    public void decreaseAmmonition() {
        ammunition--;
    }
}