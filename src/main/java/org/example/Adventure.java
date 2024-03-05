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
}
