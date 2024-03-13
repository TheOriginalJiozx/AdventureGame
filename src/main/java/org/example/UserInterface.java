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
                            System.out.println("You chose not to unlock the west room.");
                        }
                    } else {
                        currentRoom = adventure.go(Direction.WEST);
                    }
                    break;
                case "look":
                case "l":
                    lookAround();
                    break;
                case "health":
                case "hp":
                    health();
                    break;
                case "take item":
                case "take":
                case "t":
                    takeItem();
                    break;
                case "drop item":
                case "drop":
                case "d":
                    dropItem();
                    break;
                case "drop food":
                case "drop f":
                case "df":
                    dropFood();
                    break;
                case "eat":
                    eat();
                    break;
                case "take food":
                case "te":
                    takeFood();
                    break;
                case "inventory":
                case "i":
                    viewInventory();
                    break;
                case "exit":
                case "ex":
                    System.out.println("Thanks for playing!");
                    break;
                case "help":
                case "h":
                    Room currentRoom = adventure.getPlayer().getCurrentRoom();
                    System.out.println(currentRoom.helpUser(commands()));
                    helpDisplayed = true;
                    break;
                case "xyzzy": // Handle xyzzy command
                    handleXyzzy();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equalsIgnoreCase("quit"));
    }

    private void handleXyzzy() {
        Room currentRoom = adventure.getPlayer().getCurrentRoom();
        Room previousXyzzyPosition = adventure.getPlayer().teleportToXyzzyPosition();
        if (previousXyzzyPosition != null && !currentRoom.getName().equals("Room 1")) {
            System.out.println("You have teleported to the previous xyzzy position.");
        } else if (currentRoom.getName().equals("Room 1")) {
            adventure.getPlayer().saveXyzzyPosition();
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    private void eat() {
        Player player = adventure.getPlayer();
        System.out.println("Enter the name or short name of the food you want to eat:");
        String foodName = scanner.nextLine().trim();
        Food food = player.getFoodFromInventory(foodName);
        if (food == null) {
            food = player.getFoodFromInventoryByShortName(foodName);
        }
        if (food != null) {
            int healthGain = food.getHealthPoints();
            if (healthGain > 0) {
                player.increaseHealth(healthGain);
                System.out.println("You have eaten " + food.getName() + " and gained " + healthGain + " health.");
                player.removeFromInventory(food);
            } else {
                System.out.println("You cannot eat " + food.getName() + ".");
            }
        } else {
            System.out.println("You don't have such food in your inventory.");
        }
    }

    private void health() {
        Player player = adventure.getPlayer();
        int health = player.getHealth();
        System.out.println("Your current health points: " + health);
    }

    private void displayMenu() {
        System.out.println("You are in " + currentRoom.getName() + ". What would you like to do?");
        System.out.println("Enter 'go north' (n) to go north");
        System.out.println("Enter 'go south' (s) to go south");
        System.out.println("Enter 'go east' (e) to go east");
        System.out.println("Enter 'go west' (w) to go west");
        System.out.println("Enter 'look' (l) to look around");
        System.out.println("Enter 'take item' (t) to take an item");
        System.out.println("Enter 'drop item' (d) to drop an item");
        System.out.println("Enter 'take food' (te) to eat food");
        System.out.println("Enter 'health' (he) to view health");
        System.out.println("Enter 'inventory' (i) to view your inventory");
        System.out.println("Enter 'help' (h) to display this menu again");
        System.out.println("Enter 'quit' (q) to quit the game");
        choiceEntered = false;
    }

    public String commands() {
        StringBuilder commandList = new StringBuilder();
        commandList.append("'go north or n' to go north\n");
        commandList.append("'go south or s' to go south\n");
        commandList.append("'go east or e' to go east\n");
        commandList.append("'go west or w' to go west\n");
        commandList.append("'look' to look around\n");
        commandList.append("'eat' to eat\n");
        commandList.append("'health' to view health\n");
        commandList.append("'help' if you forgot which room you are in\n");
        commandList.append("'take' to pick up an item\n");
        commandList.append("'inventory' to open your inventory\n");
        commandList.append("'exit' to exit program\n");
        return commandList.toString();
    }

    private String getUserChoice() {
        choiceEntered = true;
        System.out.print(">> ");
        return scanner.nextLine().trim();
    }

    private void lookAround() {
        lookDisplayed = true;
        System.out.println("You look around the room. You see:");
        for (Item item : currentRoom.getItems()) {
            System.out.println("- " + item.getName());
        }
        for (Food food : currentRoom.getFoods()) {
            System.out.println("- " + food.getName());
        }
    }

    private void takeItem() {
        System.out.println("Enter the name or short name of the item you want to take: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.takeItemFromRoom(itemName);
        if (item == null) {
            item = adventure.takeItemFromRoomByShortName(itemName);
        }
        if (item != null) {
            adventure.getPlayer().addToInventory(item);
            System.out.println("You have taken " + item.getName() + ".");
        } else {
            System.out.println("There is no such item in this room.");
        }
    }

    private void takeFood() {
        if (!lookDisplayed) {
            System.out.println("You need to look around first.");
            return;
        }
        System.out.println("Enter the name of the food you want to take:");
        String foodName = scanner.nextLine().trim();
        Food food = currentRoom.takeFood(foodName);
        if (food != null) {
            adventure.getPlayer().addToInventory(food);
            System.out.println("You have taken: " + food.getName());
        } else {
            System.out.println("There is no such food in this room.");
        }
    }

    private void dropItem() {
        System.out.println("Enter the name or short name of the item you want to drop: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.dropItemFromInventory(itemName);
        if (item == null) {
            item = adventure.dropItemFromInventoryByShortName(itemName);
        }
        if (item != null) {
            adventure.getPlayer().getCurrentRoom().addItems(item);
            System.out.println("You have dropped " + item.getName() + ".");
        } else {
            System.out.println("You don't have such item in your inventory.");
        }
    }

    private void dropFood() {
        System.out.println("Enter the name or short name of the food you want to drop:");
        String foodName = scanner.nextLine().trim();
        Food food = adventure.getPlayer().dropFood(foodName);
        if (food == null) {
            food = adventure.getPlayer().dropFoodByShortName(foodName);
        }
        if (food != null) {
            adventure.getPlayer().getCurrentRoom().addFoods(food);
            System.out.println("You have dropped " + food.getName() + ".");
        } else {
            System.out.println("You don't have such food in your inventory.");
        }
    }

    private void viewInventory() {
        ArrayList<Item> inventory = adventure.getPlayer().getInventoryItems();
        ArrayList<Food> inventoryFood = adventure.getPlayer().getInventoryFood();
        if (inventory.isEmpty() && inventoryFood.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("Your inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName());
        }
        for (Food food : inventoryFood) {
            System.out.println("- " + food.getName());
        }
    }
}