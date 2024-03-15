package org.example;

public class RangedWeapon extends Weapon {
    private int tries;
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
