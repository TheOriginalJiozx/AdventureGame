package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Enemy {
    private String name;
    private int health;
    private int damage;
    private List<String> vulnerableWeapons;
    private boolean hasAttacked;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.vulnerableWeapons = new ArrayList<>();
        this.hasAttacked = false;
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

    public void playerEntered(Player player, Weapon weapon) {
        if (!hasAttacked && !isDefeated()) {
            if (player.hasAttacked()) { // Check if the player has initiated an attack
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> startAttack(player), 10, TimeUnit.SECONDS);
                scheduler.shutdown();
                hasAttacked = true;
            }
        }
    }

    public void startAttack(Player player) {
        Runnable attackTask = () -> {
            int attackCount = 0;
            while (player.getHealth() > 0 && this.getHealth() > 0 && attackCount < 1) {
                int damageDealtByEnemy = this.getDamage();
                player.decreaseHealth(damageDealtByEnemy);
                int playerHealthAfterAttack = player.getHealth();
                int enemyHealthAfterAttack = this.getHealth();

                UserInterface ui = new UserInterface();
                if (playerHealthAfterAttack <= 0) {
                    ui.gameOver();
                    break;
                } else {
                    ui.enemyAttacked(this.getName(), damageDealtByEnemy, playerHealthAfterAttack, enemyHealthAfterAttack);
                }

                attackCount++;
            }
        };

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(attackTask);
        executor.shutdown();
    }

    public void playerAttacked() {
        hasAttacked = true; // Set hasAttacked flag to true when the player attacks
    }

    public boolean hasAttacked() {
        return this.hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}