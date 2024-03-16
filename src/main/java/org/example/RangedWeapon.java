package org.example;

public class RangedWeapon extends Weapon {
    private int tries;
    public static final int INFINITE_AMMO_CAPACITY = Integer.MAX_VALUE;
    public RangedWeapon(String name, String shortName, int damage, int tries) {
        super(name, shortName, damage);
        this.tries = tries;
    }
    public int getTries() {
        return tries;
    }

    public void decreaseTries() {
        tries--;
    }
}
