package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Item> inventoryItems;
    private ArrayList<Food> inventoryFood;
    private int health;
    private Room xyzzyRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.previousRoom = null;
        this.inventoryItems = new ArrayList<>();
        this.inventoryFood = new ArrayList<>();
        this.health = 100;
        this.xyzzyRoom = currentRoom;
    }

    public void decreaseHealth(int amount) {
        health = health+amount;
    }

    public void increaseHealth(int amount){
        health = health+amount;
    }

    public int getHealth(){
        return health;
    }

    public Room go(Direction direction) {
        Room nextRoom = null;

        if (currentRoom.getName().equals("Room 3") && currentRoom.areLightsOff()) {
            switch (direction) {
                case SOUTH:
                    if (currentRoom.getSouthRoom() != null && currentRoom.getSouthRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getSouthRoom();
                    }
                    break;
                case NORTH:
                    if (currentRoom.getNorthRoom() != null && currentRoom.getNorthRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getNorthRoom();
                    }
                    break;
                case WEST:
                    if (currentRoom.getWestRoom() != null && currentRoom.getWestRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getWestRoom();
                    }
                    break;
                case EAST:
                    if (currentRoom.getEastRoom() != null && currentRoom.getEastRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getEastRoom();
                    }
                    break;
            }
            if (nextRoom == null) {
                System.out.println("It's too dark to see anything. You can't move to other rooms until you turn on the lights.");
                return currentRoom;
            }
        } else {
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
        }

        if (nextRoom != null) {
            if (!nextRoom.hasVisited()) {
                System.out.println("You have gone to " + nextRoom.getName() + ". " + "This is a " + nextRoom.getDescription());
                nextRoom.setVisited(true);
            } else {
                System.out.println("You have gone back to " + nextRoom.getName() + ". " + "What now? ");
            }
            previousRoom = currentRoom;
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

    public Food dropFoodByShortName(String shortName) {
        for (Food food : inventoryFood) {
            if (food.getShortName().equalsIgnoreCase(shortName)) {
                inventoryFood.remove(food);
                return food;
            }
        }
        return null;
    }

    public void removeFromInventory(Food food) {
        inventoryFood.remove(food);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void saveXyzzyPosition() {
        xyzzyRoom = currentRoom;
    }

    public Room teleportToXyzzyPosition() {
        Room previousRoom = currentRoom;
        currentRoom = xyzzyRoom;
        xyzzyRoom = previousRoom;
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