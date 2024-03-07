package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.inventory = new ArrayList<>();
    }

    public void helpUser() {
        currentRoom.helpUser();
    }

    private boolean hasWall(Direction direction) {
        switch (direction) {
            case NORTH:
                return currentRoom.getNorthRoom() == null;
            case SOUTH:
                return currentRoom.getSouthRoom() == null;
            case EAST:
                return currentRoom.getEastRoom() == null;
            case WEST:
                return currentRoom.getWestRoom() == null;
            default:
                return false;
        }
    }

    public Room go(Direction direction) {
        if (hasWall(direction)) {
            System.out.println("You have hit a wall! Try again.");
            return currentRoom;
        } else {
            switch (direction) {
                case NORTH:
                    currentRoom = currentRoom.getNorthRoom();
                    break;
                case SOUTH:
                    currentRoom = currentRoom.getSouthRoom();
                    break;
                case EAST:
                    currentRoom = currentRoom.getEastRoom();
                    break;
                case WEST:
                    currentRoom = currentRoom.getWestRoom();
                    break;
            }
            System.out.println("You have gone " + direction.toString().toLowerCase() + " to " + currentRoom.getName() + ". This is a " + currentRoom.getDescription() + " " + currentRoom.getItems());
            return currentRoom;
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}