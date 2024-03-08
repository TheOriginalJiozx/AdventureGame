package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Room currentRoom;
    private List<Item> inventory;

    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void move(String direction) {
        Room nextRoom = null;
        switch (direction.toLowerCase()) {
            case "north":
                nextRoom = currentRoom.getNorthRoom();
                break;
            case "south":
                nextRoom = currentRoom.getSouthRoom();
                break;
            case "west":
                nextRoom = currentRoom.getWestRoom();
                break;
            case "east":
                nextRoom = currentRoom.getEastRoom();
                break;

        }
        if (nextRoom == null) {
            System.out.println("You can't go that way");
        } else {
            currentRoom = nextRoom;
        }
    }

    public void lookAround() {
        currentRoom.lookAround();
    }

    public void takeItem(String itemName) {
        Item item = currentRoom.findItem(itemName);
        if (item == null) {
            item = currentRoom.findItemByShortName(itemName);
        }
        if (item != null) {
            currentRoom.removeItem(item);
            inventory.add(item);
            System.out.println("You picked up: " + item.getName());
        } else {
            System.out.println("There is no " + itemName);
        }
    }

    public void dropItem(String itemName) {
        Item itemToRemove = null;

        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName) || item.getShortName().equalsIgnoreCase(itemName)) {
                itemToRemove = item;
                break;
            }
        }

        if (itemToRemove != null) {
            inventory.remove(itemToRemove);
            currentRoom.addItem(itemToRemove);
            System.out.println("You dropped: " + itemToRemove.getName());
        } else {
            System.out.println("You don't have " + itemName + " in your inventory");
        }
    }

    public void showInventory () {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName());
                {
                }
            }
        }
    }
}