package org.example;

public class Weapon extends Item {
    private int damage;
    private boolean equipped;
    private Room pickupRoom;


    public Weapon(String name, int damage, int weight, Room pickupRoom) {
        super(name, weight);
        this.damage = damage;
        this.equipped = false;
        this.pickupRoom = pickupRoom;
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

    public Room getPickupRoom() {
        return pickupRoom;
    }
}