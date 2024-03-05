package org.example;

import java.util.Scanner;

public class UserInterface {
    public UserInterface() {
        this.scanner = new Scanner(System.in);
    }
    private final Scanner scanner;

    public void startProgram() {
        String choice;

        do {
            displayMenu();
            choice = getUserChoice().toLowerCase();
            switch (choice) {
                case "go north":
                case "n":
                    Room.setCurrentRoom(AdventureGame.goNorth(Room.getCurrentRoom(), scanner));
                    break;
                case "go south":
                case "s":
                    Room.setCurrentRoom(AdventureGame.goSouth(Room.getCurrentRoom(), scanner));
                    break;
                case "go east":
                case "e":
                    Room.setCurrentRoom(AdventureGame.goEast(Room.getCurrentRoom(), scanner));
                    break;
                case "go west":
                case "w":
                    Room.setCurrentRoom(AdventureGame.goWest(Room.getCurrentRoom(), scanner));
                    break;
                case "look":
                    lookAround();
                    break;
                case "help":
                    helpUser();
                    break;
                case "exit":
                    System.out.println();
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (!choice.equals("exit"));

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("Menu");
        System.out.println("Enter 'go north or n' to go north");
        System.out.println("Enter 'go south or s' to go south");
        System.out.println("Enter 'go east or e' to go east");
        System.out.println("Enter 'go west or w' to go west");
        System.out.println("Enter 'look' to look around");
        System.out.println("Enter 'help' if you forgot which room you are in");
        System.out.println("Enter 'exit' to exit program");
        System.out.println();
        System.out.println("Enter your choice: ");
    }

    private String getUserChoice() {
        return scanner.nextLine();
    }

    private void lookAround() {

    }

    private void helpUser() {
        AdventureGame.helpUser(Room.getCurrentRoom());
    }
}