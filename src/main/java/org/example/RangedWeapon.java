package org.example;

public class RangedWeapon extends Weapon {
    private int ammonition;
    public static final int INFINITE_AMMO_CAPACITY = Integer.MAX_VALUE;

    public RangedWeapon(String name, int damage, int weight, int ammonition, Room pickupRoom) {
        super(name, damage, weight, pickupRoom);
        this.ammonition = ammonition;
    }

    public int getAmmonition() {
        return ammonition;
    }

    public void decreaseAmmonition() {
        ammonition--;
    }
}