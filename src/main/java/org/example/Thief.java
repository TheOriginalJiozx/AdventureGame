package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Thief {
    private String name;
    private int health;
    private int damage;
    private ArrayList<Item> inventoryItems;

    public Thief(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.inventoryItems = new ArrayList<>();
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

    public ArrayList<Item> getInventoryItems() {
        return inventoryItems;
    }

    public void steal(Player player, UserInterface userInterface) {
        Random random = new Random();
        boolean success = random.nextBoolean();

        if (success) {
            ArrayList<Item> playerInventory = player.getInventoryItems();
            if (!playerInventory.isEmpty()) {
                int index = random.nextInt(playerInventory.size());
                Item stolenItem = playerInventory.get(index);
                player.removeFromInventory(stolenItem); // Remove the stolen item from the player's inventory

                handleStolenItem(stolenItem, userInterface); // Handle the stolen item

                addToInventory(stolenItem); // Add the stolen item to the thief's inventory
                System.out.println(name + " successfully stole " + stolenItem.getName() + " from you");
            } else {
                System.out.println(name + " attempted to steal but failed! Your inventory is empty.");
            }
        } else {
            System.out.println(name + " attempted to steal but failed!");
        }
    }

    private void handleStolenItem(Item item, UserInterface userInterface) {
        if (item instanceof Liquid) {
            handleStolenLiquid((Liquid) item, userInterface);
        } else if (item instanceof Weapon) {
            handleStolenWeapon((Weapon) item, userInterface);
        } else if (item instanceof Food) {
            handleStolenFood((Food) item, userInterface);
        } else {
            handleStolenOtherItem(item, userInterface);
        }
    }

    private void handleStolenLiquid(Liquid liquid, UserInterface userInterface) {
        // Handle stealing liquid
        System.out.println("Thief stole liquid: " + liquid.getName());
    }

    private void handleStolenWeapon(Weapon weapon, UserInterface userInterface) {
        // Handle stealing weapon
        System.out.println("Thief stole weapon: " + weapon.getName());
    }

    private void handleStolenFood(Food food, UserInterface userInterface) {
        // Handle stealing food
        System.out.println("Thief stole food: " + food.getName());
    }

    private void handleStolenOtherItem(Item item, UserInterface userInterface) {
        // Handle stealing other items
        System.out.println("Thief stole other item: " + item.getName());
    }

    public void addToInventory(Item item) {
        inventoryItems.add(item);
    }
}