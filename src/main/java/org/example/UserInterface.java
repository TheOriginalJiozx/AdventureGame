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
                case "take":
                case "t":
                    takeItem();
                    break;
                case "drop":
                case "d":
                    dropItem();
                    break;
                case "eat":
                    eat();
                break;
                case "equip weapon":
                case "eq":
                    String weaponName = promptWeaponSelection();
                    adventure.getPlayer().equipWeapon(weaponName);
                    break;
                case "use weapon":
                case "use":
                    adventure.getPlayer().useWeapon();
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
                    System.out.println(helpUser(commands()));
                    helpDisplayed = true;
                    break;
                case "xyzzy":
                    handleXyzzy();
                    break;
                case "turn on":
                case "on":
                    if (adventure.tryTurnOnLights()) {
                        System.out.println(adventure.turnOnLightsRoom3());
                    } else {
                        System.out.println("You can't turn on the lights here.");
                    }
                    break;
                case "turn off":
                case "off":
                    if (adventure.tryTurnOffLights()) {
                        System.out.println(adventure.turnOffLightsRoom3());
                    } else {
                        System.out.println("You can't turn off the lights here.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }

    public String helpUser(String commands) {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("You are in room: ").append(currentRoom.getName()).append("\n");
        helpMessage.append("Description: ").append(currentRoom.getDescription()).append("\n");
        helpMessage.append("Available commands:\n");
        helpMessage.append(commands);
        String lightsStatus = currentRoom.areLightsOff() ? "off" : "on";
        helpMessage.append("The lights in the room are: ").append(lightsStatus).append("\n");
        return helpMessage.toString();
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

    public String promptWeaponSelection() {
        System.out.println("Enter the name of the weapon you want to equip:");
        return scanner.nextLine();
    }

    private void eat() {
        Player player = adventure.getPlayer();
        System.out.println("Enter the name or short name of the food you want to eat:");
        String itemName = scanner.nextLine().trim();
        Item item = player.getItemFromInventory(itemName);
        if (item == null) {
            item = player.getItemFromInventoryByShortName(itemName);
        }
        if (item != null && item instanceof Food) {
            Food food = (Food) item;
            int healthChange = food.getHealthPoints();
            if (healthChange != 0) {
                if (healthChange > 0) {
                    player.increaseHealth(healthChange);
                    System.out.println("You have eaten " + food.getName() + " and gained " + healthChange + " health.");
                } else {
                    player.decreaseHealth(Math.abs(healthChange));
                    System.out.println("You have eaten " + food.getName() + " and lost " + Math.abs(healthChange) + " health.");
                }
                player.removeFromInventory(food);
                if (player.getHealth() <= 0) {
                    System.out.println("You have died!");
                    System.exit(0);
                }
            } else {
                System.out.println("This item is not edible.");
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
        System.out.println("Enter 'take' (t) to take something");
        System.out.println("Enter 'drop' (d) to drop something");
        System.out.println("Enter 'eat' to eat food");
        System.out.println("Enter 'equip weapon' (eq) to equip a weapon");
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
        commandList.append("'look' or 'l' to look around\n");
        commandList.append("'eat' to eat\n");
        commandList.append("'health' or 'hp' to view health\n");
        commandList.append("'take' or 't' to pick up something\n");
        commandList.append("'equip weapon' or 'eq' to equip a weapon\n");
        commandList.append("'drop' or 'd' to drop something\n");
        commandList.append("'health' or 'he' to view your health points\n");
        commandList.append("'help' or 'h' if you forgot which room you are in\n");
        commandList.append("'inventory' or 'i' to open your inventory\n");
        commandList.append("'quit' or 'q' to exit program\n");
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

    private void viewInventory() {
        ArrayList<Item> inventory = adventure.getPlayer().getInventoryItems();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
            return;
        }
        System.out.println("Your inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName());
        }
    }
}