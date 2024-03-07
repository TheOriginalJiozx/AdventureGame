package org.example;

import java.util.Scanner;
import java.util.ArrayList;

public class UserInterface {
    private final Scanner scanner;
    private Adventure adventure;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();
    }

    public void startProgram() {
        String choice;

        do {
            displayMenu();
            choice = getUserChoice().toLowerCase();
            switch (choice) {
                case "go north":
                case "n":
                    adventure.go(Direction.NORTH);
                    break;
                case "go south":
                case "s":
                    adventure.go(Direction.SOUTH);
                    break;
                case "go east":
                case "e":
                    adventure.go(Direction.EAST);
                    break;
                case "go west":
                case "w":
                    adventure.go(Direction.WEST);
                    break;
                case "look":
                    for (Item item : adventure.lookAround()) {
                        System.out.println(item.getName());
                    }
                    break;
                case "help":
                    adventure.helpUser();
                    break;
                case "take":
                    ArrayList<Item> takenItems = adventure.takeItemsFromRoom();
                    if (!takenItems.isEmpty()) {
                        for (Item item : takenItems) {
                            adventure.addToInventory(item);
                        }
                        System.out.print("You have taken: ");
                        for (Item item : takenItems) {
                            System.out.print(item.getName() + ", short name: " + item.getShortName());
                        }
                        System.out.println();
                    } else {
                        System.out.println("There are no items to take.");
                    }
                    break;
                case "inventory":
                    ArrayList<Item> playerInventory = adventure.getPlayerInventory();
                    if (!playerInventory.isEmpty()) {
                        System.out.println("Your inventory includes: ");
                        for (Item item : playerInventory) {
                            System.out.println(item.getName() + ", short name: " + item.getShortName());
                        }
                    } else {
                        System.out.println("Your inventory is empty.");
                    }
                    break;
                case "drop":
                    adventure.dropAllTakenItems();
                    break;
                case "exit":
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
        System.out.println("Enter 'take' to pick up an item");
        System.out.println("Enter 'inventory' to open your inventory");
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
        commandList.append("'take' to pick up an item\n");
        commandList.append("'inventory' to open your inventory\n");
        commandList.append("'exit' to exit program\n");
        return commandList.toString();
    }

    private String getUserChoice() {
        return scanner.nextLine().trim().toLowerCase();
    }
}