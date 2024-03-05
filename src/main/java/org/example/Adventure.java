package org.example;
import java.util.Scanner;

public class Adventure {
    public static Room goNorth(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();

        if (room.getName().equals("Room 1") || room.getName().equals("Room 2") || room.getName().equals("Room 3")) {
            System.out.println("You have hit a wall! Try again.");
            System.out.println();
            return room;
        } else {
            System.out.println(STR."You have gone north to \{room.getNorthRoom().getName()}." + " This is a " + Room.getCurrentRoom().getDescription());
            System.out.println();
            return room.getNorthRoom();
        }
    }

    public static Room goSouth(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        if (room.getName().equals("Room 2") || room.getName().equals("Room 7") || room.getName().equals("Room 8") || room.getName().equals("Room 9")) {
            System.out.println("You have hit a wall! Try again.");
            System.out.println();
            return room;
        } else {
            System.out.println(STR."You have gone south to \{room.getSouthRoom().getName()}." + " This is a " + Room.getCurrentRoom().getDescription());
            System.out.println();
            return room.getSouthRoom();
        }
    }

    public static Room goEast(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        if (room.getName().equals("Room 3") || room.getName().equals("Room 4") || room.getName().equals("Room 9")) {
            System.out.println("You have hit a wall! Try again.");
            System.out.println();
            return room;
        } else {
            System.out.println(STR."You have gone east to \{room.getEastRoom().getName()}." + " This is a " + Room.getCurrentRoom().getDescription());
            System.out.println();
            return room.getEastRoom();
        }
    }

    public static Room goWest(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        if (room.getName().equals("Room 1") || room.getName().equals("Room 4") || room.getName().equals("Room 6") || room.getName().equals("Room 7")) {
            System.out.println("You have hit a wall! Try again.");
            System.out.println();
            return room;
        } else {
            System.out.println(STR."You have gone west to \{room.getWestRoom().getName()}." + " This is a " + Room.getCurrentRoom().getDescription());
            System.out.println();
            return room.getWestRoom();
        }
    }

    static void helpUser(Room room) {
        System.out.println();
        room.helpUser();
        System.out.println();
    }

    static void lookAround(Room room) {
        System.out.println();
        room.lookAround();
        System.out.println();
    }

    public static Room getRoom(String roomName) {
        Room room1 = new Room("Room 1", "room with no distinct features. You see two doors. Choose wisely.");
        Room room2 = new Room("Room 2", "room with spiders crawling down the walls. There are two doors. They are crawling over to you, choose a door!.");
        Room room3 = new Room("Room 3", "room with goblins eating dead dwarves. You see two doors. Choose a door, quick!");
        Room room4 = new Room("Room 4", "room with dragons dancing. You see two doors. Don't worry, they only kill you if you run.");
        Room room5 = new Room("Room 5", "room full of gold and diamonds. Congralutions!");
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

        switch (roomName) {
            case "Room 1":
                return room1;
            case "Room 2":
                return room2;
            case "Room 3":
                return room3;
            case "Room 4":
                return room4;
            case "Room 5":
                return room5;
            case "Room 6":
                return room6;
            case "Room 7":
                return room7;
            case "Room 8":
                return room8;
            case "Room 9":
                return room9;
            default:
                return null;
        }
    }
}
