package org.example;

import java.util.Scanner;

public class Adventure {
    private Room currentRoom;

    public Adventure() {
        this.currentRoom = createWorld();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private Room createWorld() {
        Room room1 = new Room("Room 1", "room with no distinct features. You see two doors. Choose wisely.");
        Room room2 = new Room("Room 2", "room with spiders crawling down the walls. There are two doors. They are crawling over to you, choose a door!.");
        Room room3 = new Room("Room 3", "room with goblins eating dead dwarves. You see two doors. Choose a door, quick!");
        Room room4 = new Room("Room 4", "room with dragons dancing. You see two doors. Don't worry, they only kill you if you run.");
        Room room5 = new Room("Room 5", "room full of gold and diamonds. Congratulations!");
        Room room6 = new Room("Room 6", "room with trolls ready to eat you. You see two doors. RUN!");
        Room room7 = new Room("Room 7", "room with a giant demonic bear. You see two doors. Play dead and pray or choose a door?");
        Room room8 = new Room("Room 8", "room with yellow slime everywhere. You see three doors. Choose a door.");
        Room room9 = new Room("Room 9", "room with giant rats ready to beat you down. You see two doors. I hope you brought giant traps with you. Choose a door!");

        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room4.setNorthRoom(room1);
        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room7.setEastRoom(room8);
        room7.setNorthRoom(room4);
        room4.setSouthRoom(room7);
        room8.setNorthRoom(room5);

        return room1;
    }

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

    public void helpUser(Room room) {
        System.out.println();
        room.helpUser();
    }

    public void lookAround(Room room) {
        System.out.println();
        room.lookAround();
        System.out.println();
    }
}