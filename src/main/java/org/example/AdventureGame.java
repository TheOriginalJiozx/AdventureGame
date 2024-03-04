package org.example;
import java.util.Scanner;

public class AdventureGame {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.startProgram();
    }

    public static void goNorth(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        System.out.println("You have gone north!");
        if (room = Room1, Room2, Room3) {
            System.out.println("You have hit a wall! Try again.");
        }
    }

    public static void goSouth(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        System.out.println("You have gone south!");
        if (room = Room2, Room7, Room8, Room9) {
            System.out.println("You have hit a wall! Try again.");
        }
    }

    public static void goEast(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        System.out.println("You have gone east!");
        if (room = Room3, Room4, Room9) {
            System.out.println("You have hit a wall! Try again.");
        }
    }

    public static void goWest(Room room, Scanner scanner) {
        System.out.println();
        scanner.nextLine();
        System.out.println("You have gone west!");
        if (room = Room1, Room4, Room6, Room7) {
            System.out.println("You have hit a wall! Try again.");
        }
    }
}