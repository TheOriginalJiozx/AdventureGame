package org.example;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private Adventure adventure;
    private Room currentRoom;
    private boolean helpDisplayed;
    private boolean choiceEntered;
    private boolean lookDisplayed;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();
        this.currentRoom = adventure.getPlayer().getCurrentRoom();
        this.helpDisplayed = false;
        this.choiceEntered = false;
        this.lookDisplayed = false;
    }

    public void startProgram() {
        String choice;

        do {
            if (!helpDisplayed && !choiceEntered) {
                displayMenu();
            }
            choice = getUserChoice().toLowerCase();
            switch (choice) {
                case "go north":
                case "n":
                    currentRoom = adventure.go(Direction.NORTH);
                    break;
                case "go south":
                case "s":
                    currentRoom = adventure.go(Direction.SOUTH);
                    break;
                case "go east":
                case "e":
                    currentRoom = adventure.go(Direction.EAST);
                    break;
                case "go west":
                case "w":
                    if (adventure.tryUnlockWestRoom()) {
                        System.out.println("The west room is locked. Enter 'unlock' to unlock it.");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            adventure.unlockWestRoom();
                            System.out.println("You have unlocked the west room!");
                        } else {
                            System.out.println("The west room remains locked. Try again later.");
                        }
                    } else {
                        currentRoom = adventure.go(Direction.WEST);
                    }
                    break;
                case "unlock":
                    String unlockMessage = adventure.unlockWestRoom();
                    System.out.println(unlockMessage);
                    break;
                case "look":
                    lookAround();
                    break;
                case "take":
                    takeItem();
                    break;
                case "drop":
                    dropItem();
                    break;
                case "help":
                    System.out.println(adventure.getPlayer().getCurrentRoom().helpUser(commands()));
                    helpDisplayed = true;
                    break;
                case "inventory":
                    viewInventory();
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                case "xyzzy": // Handle xyzzy command
                    handleXyzzy();
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            choiceEntered = true;
        } while (!choice.equals("exit"));

        scanner.close();
    }

    // Method to handle xyzzy command
    private void handleXyzzy() {
        Room currentRoom = adventure.getPlayer().getCurrentRoom();
        Room previousXyzzyPosition = adventure.getPlayer().teleportToXyzzyPosition();
        if (previousXyzzyPosition != null && !currentRoom.getName().equals("Room 1")) {
            System.out.println("You have teleported to the previous xyzzy position.");
        } else if (currentRoom.getName().equals("Room 1")) {
            adventure.getPlayer().saveXyzzyPosition();
            System.out.println("You have teleported back to: " + currentRoom.getName());
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    private void lookAround() {
        ArrayList<Item> itemsInRoom = adventure.lookAround();
        if (!itemsInRoom.isEmpty()) {
            for (Item item : itemsInRoom) {
                System.out.println(item.getName());
            }
            lookDisplayed = true;
        } else {
            System.out.println("There are no items in this room.");
        }
    }

    private void takeItem() {
        System.out.println("Enter the name or short name of the item you want to take: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.takeItemFromRoomByShortName(itemName);
        if (item == null) {
            item = adventure.takeItemFromRoom(itemName);
        }
        if (item != null) {
            adventure.addToInventory(item);
            System.out.println("You have taken " + item.getName() + ".");
        } else {
            System.out.println("There is no such item in this room.");
        }
    }

    private void dropItem() {
        System.out.println("Enter the name or short name of the item you want to drop: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.dropItemFromInventoryByShortName(itemName);
        if (item == null) {
            item = adventure.dropItemFromInventory(itemName);
        }
        if (item != null) {
            adventure.getPlayer().getCurrentRoom().addItems(item);
            System.out.println("You have dropped " + item.getName() + ".");
        } else {
            System.out.println("You don't have such item in your inventory.");
        }
    }

    private void viewInventory() {
        ArrayList<Item> inventoryItems = adventure.getPlayerInventory();
        if (!inventoryItems.isEmpty()) {
            System.out.println("Your inventory includes: ");
            for (Item item : inventoryItems) {
                System.out.println(item.getName() + ", short name: " + item.getShortName());
            }
        } else {
            System.out.println("Your inventory is empty.");
        }
    }

    private void displayMenu() {
        System.out.println("Menu");
        System.out.println("Enter 'go north or n' to go north");
        System.out.println("Enter 'go south or s' to go south");
        System.out.println("Enter 'go east or e' to go east");
        System.out.println("Enter 'go west or w' to go west");
        System.out.println("Enter 'look' to look around");
        System.out.println("Enter 'help' if you forgot which room you are in and to see commands");
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