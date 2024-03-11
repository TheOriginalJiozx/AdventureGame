package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private boolean westRoomLocked;
    private boolean westRoomUnlocked;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new ArrayList<>();
        this.westRoomLocked = true;
        this.westRoomUnlocked = false;
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
                if (currentRoom.getName().equals("Room 9") && currentRoom.isWestLocked()) {
                    System.out.println("The room is locked. Enter 'unlock' to unlock the room.");
                    return currentRoom;
                }
                nextRoom = currentRoom.getWestRoom();
                break;
        }

        if (nextRoom != null) {
            if (!nextRoom.hasVisited()) {
                System.out.println("You have gone " + direction.toString().toLowerCase() + " to " + nextRoom.getName() + ". This is a " + nextRoom.getDescription());
                nextRoom.setVisited(true);
            } else {
                System.out.println("You have gone back to " + nextRoom.getName());
            }
            currentRoom = nextRoom;
        } else {
            System.out.println("You have hit a wall! Try again.");
        }

        return currentRoom;
    }

    public void unlockWestRoom() {
        if (currentRoom.getName().equals("Room 9")) {
            currentRoom.unlockWestRoom();
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public Item dropItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                return item;
            }
        }
        return null;
    }
}