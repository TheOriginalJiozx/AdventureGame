package org.example;

import java.util.Scanner;

public class Player {
    private Room currentRoom;
    private boolean hasWall(Room room, Direction direction) {
        switch (direction) {
            case NORTH:
                return room.getNorthRoom() == null;
            case SOUTH:
                return room.getSouthRoom() == null;
            case EAST:
                return room.getEastRoom() == null;
            case WEST:
                return room.getWestRoom() == null;
            default:
                return false;
        }
    }
    public Room go(Direction direction, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        Room nextRoom = null;
        if (hasWall(currentRoom, direction)) {
            System.out.println("You have hit a wall! Try again.");
            System.out.println();
            return currentRoom;
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
            System.out.println("You have gone " + direction.toString().toLowerCase() + " to " + nextRoom.getName() + ". This is a " + nextRoom.getDescription());
            System.out.println();
            currentRoom = nextRoom;
            return nextRoom;
        }
    }
}
