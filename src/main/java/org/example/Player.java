package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventoryItems; // Separate list for items
    private ArrayList<Food> inventoryFood; // Separate list for food
    private int health;
    private Room xyzzyRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventoryItems = new ArrayList<>();
        this.inventoryFood = new ArrayList<>();
        this.health = 100;
        this.xyzzyRoom = currentRoom;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
    }

    public void increaseHealth(int amount){
        health += amount;
    }

    public int getHealth(){
        return health;
    }

    public Room go(Direction direction) {
        Room nextRoom = null;
        switch (direction) {
            case NORTH:
                nextRoom = currentRoom.getNorthRoom();
                break;
            case SOUTH:
                nextRoom = currentRoom.getSouthRoom();
                break;
            case EAST:
                nextRoom = currentRoom.getEastRoom();
                break;
            case WEST:
                nextRoom = currentRoom.getWestRoom();
                break;
        }

        if (nextRoom != null) {
            if (!nextRoom.hasVisited()) {
                System.out.println("You have gone to " + nextRoom.getName() + ". " + "This is a " + nextRoom.getDescription());
                nextRoom.setVisited(true);
            } else {
                System.out.println("You have gone back to " + nextRoom.getName() + ". " + "What now? ");
            }
            currentRoom = nextRoom;
        } else {
            System.out.println("You have hit a wall! Try again.");
        }

        return currentRoom;
    }

    public Food getFoodFromInventory(String name) {
        for (Food food : inventoryFood) {
            if (food.getName().equalsIgnoreCase(name)) {
                return food;
            }
        }
        return null;
    }

    public Food getFoodFromInventoryByShortName(String shortName) {
        for (Food food : inventoryFood) {
            if (food.getShortName().equalsIgnoreCase(shortName)) {
                return food;
            }
        }
        return null;
    }

    public Item getItemFromInventory(String name) {
        for (Item item : inventoryItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void removeFromInventory(Food food) {
        inventoryFood.remove(food);
    }

    public void removeFromInventory(Item item) {
        inventoryItems.remove(item);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void saveXyzzyPosition() {
        xyzzyRoom = currentRoom;
    }

    public Room teleportToXyzzyPosition() {
        Room previousRoom = currentRoom; // Store the current room as the previous room
        currentRoom = xyzzyRoom; // Set the current room to the xyzzyRoom
        xyzzyRoom = previousRoom; // Update xyzzyRoom to the previous room for future teleportations
        System.out.println("You have teleported back to: " + currentRoom.getName());
        return currentRoom;
    }

    public void addToInventory(Food food) {
        inventoryFood.add(food);
    }

    public void addToInventory(Item item) {
        inventoryItems.add(item);
    }

    public ArrayList<Food> getInventoryFood() {
        return inventoryFood;
    }

    public ArrayList<Item> getInventoryItems() {
        return inventoryItems;
    }

    public Food dropFood(String name) {
        for (Food food : inventoryFood) {
            if (food.getName().equalsIgnoreCase(name)) {
                inventoryFood.remove(food);
                return food;
            }
        }
        return null;
    }

    public Item dropItem(String name) {
        for (Item item : inventoryItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                inventoryItems.remove(item);
                return item;
            }
        }
        return null;
    }
}