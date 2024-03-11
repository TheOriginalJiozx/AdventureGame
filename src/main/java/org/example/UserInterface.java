package org.example;

import java.util.Scanner;
import java.util.ArrayList;

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
                    currentRoom = adventure.go(Direction.WEST);
                    break;
                case "unlock":
                    adventure.getPlayer().unlockWestRoom();
                    break;
                case "look":
                    lookDisplayed = true;
                    for (Item item : adventure.lookAround()) {
                        System.out.println(item.getName());
                    }
                    break;
                case "help":
                    System.out.println(adventure.getPlayer().getCurrentRoom().helpUser(commands()));
                    helpDisplayed = true;
                    break;
                case "take":
                    if (!lookDisplayed) {
                        System.out.println("You need to look around before taking items.");
                        break;
                    }
                    ArrayList<Item> roomItems = adventure.lookAround();
                    if (!roomItems.isEmpty()) {
                        System.out.println("Enter the name or short name of the item(s) you want to take (comma-separated):");
                        String itemsToTakeInput = scanner.nextLine().trim().toLowerCase();
                        String[] itemsToTake = itemsToTakeInput.split(",");
                        ArrayList<Item> takenItems = new ArrayList<>();
                        for (String itemName : itemsToTake) {
                            boolean itemFound = false;
                            for (Item item : roomItems) {
                                if (item.getName().equalsIgnoreCase(itemName.trim())) {
                                    itemFound = true;
                                    takenItems.add(item);
                                    adventure.takeItemFromRoom(item.getName());
                                    adventure.addToInventory(item);
                                    break;
                                } else if (item.getShortName().equalsIgnoreCase(itemName.trim())) {
                                    itemFound = true;
                                    Item takenItem = adventure.takeItemFromRoomByShortName(itemName.trim());
                                    if (takenItem != null) {
                                        takenItems.add(takenItem);
                                        adventure.addToInventory(takenItem);
                                    } else {
                                        System.out.println("Item '" + itemName.trim() + "' not found in the room.");
                                    }
                                    break;
                                }
                            }
                            if (!itemFound) {
                                System.out.println("Item '" + itemName.trim() + "' not found in the room.");
                            }
                        }
                        if (!takenItems.isEmpty()) {
                            System.out.print("You have taken: ");
                            for (Item item : takenItems) {
                                System.out.print(item.getName() + ", short name: " + item.getShortName() + " ");
                            }
                            System.out.println();
                        } else {
                            System.out.println("No items taken.");
                        }
                    } else {
                        System.out.println("You see no items. Look for items to take!");
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
                    ArrayList<Item> playerInventoryDrop = adventure.getPlayerInventory();
                    if (!playerInventoryDrop.isEmpty()) {
                        System.out.println("Enter the name or short name of the item(s) you want to drop (comma-separated):");
                        String itemsToDropInput = scanner.nextLine().trim().toLowerCase();
                        String[] itemsToDrop = itemsToDropInput.split(",");
                        ArrayList<Item> droppedItems = new ArrayList<>();
                        for (String itemName : itemsToDrop) {
                            Item item = adventure.dropItemFromInventory(itemName.trim());
                            if (item == null) {
                                item = adventure.dropItemFromInventoryByShortName(itemName.trim());
                            }
                            if (item != null) {
                                droppedItems.add(item);
                            } else {
                                System.out.println("Item '" + itemName.trim() + "' not found in your inventory.");
                            }
                        }
                        if (!droppedItems.isEmpty()) {
                            System.out.print("You have dropped: ");
                            for (Item item : droppedItems) {
                                System.out.print(item.getName() + ", short name: " + item.getShortName() + " ");
                            }
                            System.out.println();
                        } else {
                            System.out.println("No items dropped.");
                        }
                    } else {
                        System.out.println("Your inventory is empty.");
                    }
                    break;
                case "exit":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
            choiceEntered = true;
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