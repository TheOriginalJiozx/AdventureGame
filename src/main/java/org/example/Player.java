package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public ArrayList<Item> lookAround() {
        return currentRoom.getItems();
    }

    public Player lookAround(Item item) {
        // Additional logic to search for item in the current room
        return this;
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
}