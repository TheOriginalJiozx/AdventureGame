package org.example;

import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final Adventure adventure;
    private final Map map;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();
        this.map = new Map();
    }

    public void startProgram() {
        String choice;

        do {
            displayMenu();
            choice = getUserChoice().toLowerCase();
            switch (choice) {
                case "go north":
                case "n":
                    adventure.go(Direction.NORTH, scanner);
                    break;
                case "go south":
                case "s":
                    adventure.go(Direction.SOUTH, scanner);
                    break;
                case "go east":
                case "e":
                    adventure.go(Direction.EAST, scanner);
                    break;
                case "go west":
                case "w":
                    adventure.go(Direction.WEST, scanner);
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

    public String commands() {
        StringBuilder commandList = new StringBuilder();
        commandList.append("'go north or n' to go north\n");
        commandList.append("'go south or s' to go south\n");
        commandList.append("'go east or e' to go east\n");
        commandList.append("'go west or w' to go west\n");
        commandList.append("'look' to look around\n");
        commandList.append("'help' if you forgot which room you are in\n");
        commandList.append("'exit' to exit program\n");
        return commandList.toString();
    }

    private String getUserChoice() {
        return scanner.next().trim().toLowerCase();
    }

    private void lookAround() {
      adventure.lookAround(map.getCurrentRoom());
    }

    private void helpUser() {
        adventure.helpUser(map.getCurrentRoom());
    }
}