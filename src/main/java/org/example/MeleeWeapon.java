package org.example;

public class MeleeWeapon extends Weapon {
    private int usage;

    public MeleeWeapon(String name, int weight, int damage, Room pickupRoom) {
        super(name, damage, weight, pickupRoom);
    }
}