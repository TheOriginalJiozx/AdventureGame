package org.example;

public class PassiveEnemy extends Enemy {

    public PassiveEnemy(String name, int health, int damage) {
        super(name, health, damage);
    }

    @Override
    public void playerEntered(Player player, Weapon weapon) {
        if (player.hasAttacked()) { // Check if the player has initiated an attack
            super.playerEntered(player, weapon);
        }
    }
}

