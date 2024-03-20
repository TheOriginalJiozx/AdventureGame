package org.example;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    MapConnections mapConnections = new MapConnections();
    private Adventure adventure;
    private Room currentRoom;
    private boolean helpDisplayed;
    private boolean choiceEntered;
    private boolean lookDisplayed;
    private Player player;
    private boolean viewInventory;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();
        this.currentRoom = adventure.getPlayer().getCurrentRoom();
        this.helpDisplayed = false;
        this.choiceEntered = false;
        this.lookDisplayed = false;
        this.viewInventory = false;
        this.player = adventure.getPlayer();
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
                    if (adventure.tryUnlockNorthRoom()) {
                        System.out.println("The north room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock.");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            adventure.unlockNorthRoom();
                            System.out.println("You have unlocked the north room!");
                        } else {
                            System.out.println("You chose not to unlock the north room.");
                        }
                    } else {
                        currentRoom.tryDirection(Direction.NORTH);
                        currentRoom = adventure.go(Direction.NORTH);
                    }
                    break;
                case "go south":
                case "s":
                    if (adventure.tryUnlockSouthRoom()) {
                        System.out.println("The south room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock..");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            adventure.unlockSouthRoom();
                            System.out.println("You have unlocked the south room!");
                        } else {
                            System.out.println("You chose not to unlock the south room.");
                        }
                    } else {
                        currentRoom.tryDirection(Direction.SOUTH);
                        currentRoom = adventure.go(Direction.SOUTH);
                    }
                    break;
                case "go east":
                case "e":
                    if (adventure.tryUnlockEastRoom()) {
                        System.out.println("The east room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock..");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            adventure.unlockEastRoom();
                            System.out.println("You have unlocked the east room!");
                        } else {
                            System.out.println("You chose not to unlock the east room.");
                        }
                    } else {
                        currentRoom.tryDirection(Direction.EAST);
                        currentRoom = adventure.go(Direction.EAST);
                    }
                    break;
                case "go west":
                case "w":
                    if (adventure.tryUnlockWestRoom()) {
                        System.out.println("The west room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock..");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            adventure.unlockWestRoom();
                            System.out.println("You have unlocked the west room!");
                        } else {
                            System.out.println("You chose not to unlock the west room.");
                        }
                    } else {
                            currentRoom.tryDirection(Direction.WEST);
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
                    adventure.getPlayer().takeItem(adventure, this);
                    break;
                case "drop":
                case "d":
                    adventure.getPlayer().dropItem(adventure,this);
                    break;
                case "craft":
                case "c":
                    playerCraftItems();
                    break;
                case "eat":
                    eat();
                    break;
                case "drink":
                    drink();
                break;
                case "equip weapon":
                case "eq":
                    String weaponName = promptWeaponSelection();
                    adventure.getPlayer().equipWeapon(weaponName, this);
                    break;
                case "attack enemy":
                case "attack":
                    adventure.getPlayer().useWeapon();
                    break;
                case "inventory":
                case "inv":
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
                        Room currentRoom = player.getCurrentRoom();
                        if (currentRoom.getName().equals("Mine Tunnels")) {
                            System.out.println("The lights are already on.");
                        } else {
                            System.out.println("You can't turn on the lights here.");
                        }
                    }
                    break;
                case "turn off":
                case "off":
                    if (adventure.tryTurnOffLights()) {
                        System.out.println(adventure.turnOffLightsRoom3());
                    } else {
                        Room currentRoom = player.getCurrentRoom();
                        if (currentRoom.getName().equals("Mine Tunnels")) {
                            System.out.println("The lights are already off.");
                        } else {
                            System.out.println("You can't turn off the lights here.");
                        }
                    }
                    break;
                case "mute":
                    currentRoom.music.stopMusic();
                    break;
                case "resume":
                    currentRoom.music.playMusic();
                    break;
                case "map":
                    mapConnections.displayMap();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }

    private void health() {
        Player player = adventure.getPlayer();
        int health = player.getHealth();
        System.out.println("Your current health points: " + health);
    }

    private void lookAround() {
        lookDisplayed = true;
        if (currentRoom.allDirectionsTried()) {
            printRoomItems();
            printAvailableDirections();
        } else {
            printRoomItems();
        }
    }

    public boolean isLookDisplayed() {
        return lookDisplayed;
    }

    /*private void takeItem() {
        if (!lookDisplayed) {
            System.out.println("You have to look before taking an item. Can't take what you can't see!");
        } else {
            System.out.println("Enter the name or short name of the item you want to take: ");
            String itemName = scanner.nextLine().trim().toLowerCase();
            Item item = adventure.takeItemFromRoom(itemName);
            if (item == null) {
                item = adventure.takeItemFromRoomByShortName(itemName);
                if (item == null) {
                    System.out.println("The item \"" + itemName + "\" does not exist in this room.");
                    return;
                }
            }
            if (item != null) {
                Player player = adventure.getPlayer();
                int currentWeight = player.getInventoryWeight();
                int maxCarry = item.getMaxCarry();
                int itemWeight = item.getWeight();
                if (currentWeight + itemWeight > maxCarry) {
                    System.out.println("You cannot pick up this item as it would make your inventory exceed the weight limit.");
                } else if (currentWeight + itemWeight == maxCarry) {
                    adventure.getPlayer().addToInventory(item);
                    System.out.println("You have taken " + item.getName() + ", short name: " + item.getShortName() + ". It weighs: " + item.getWeight() + " grams.");
                    System.out.println("You cannot pick up more items until you drop something from your inventory.");
                } else {
                    adventure.getPlayer().addToInventory(item);
                    System.out.println("You have taken " + item.getName() + ", short name: " + item.getShortName() + ". It weighs: " + item.getWeight() + " grams.");
                }
            }
        }
    }*/

    public String takeItemLookPrompt() {
        return "You have to look before taking an item. Can't take what you can't see!";
    }

    public Item enterItemNamePrompt() {
        System.out.println("Enter the name or short name of the item you want to take: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.takeItemFromRoom(itemName);
        if (item == null) {
            item = adventure.takeItemFromRoomByShortName(itemName);
            if (item == null) {
                System.out.println("The item \"" + itemName + "\" does not exist in this room.");
            }
        }
        return item;
    }

    /*private void dropItem() {
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
    }*/

    public String promptDropItemName() {
        System.out.println("Enter the name or short name of the item you want to drop: ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void dropItemViewInventoryPrompt() {
        System.out.println("You have to view your inventory before dropping an item.");
    }

    private void eat() {
        if (!viewInventory) {
            System.out.println("You have to open your inventory to pick something to eat before eating.");
        } else {
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
                if (healthChange > 0) {
                    player.increaseHealth(healthChange);
                    System.out.println("You have eaten " + food.getName() + " and gained " + healthChange + " health.");
                } else if (healthChange < 0) {
                    System.out.println("This doesn't look healthy. Are you sure you want to eat this? (yes/no)");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("yes")) {
                        player.decreaseHealth(Math.abs(healthChange));
                        System.out.println("You have eaten " + food.getName() + " and lost " + Math.abs(healthChange) + " health.");
                    } else if (input.equals("no")) {
                        System.out.println("Would you like to keep or drop this food? (keep/drop)");
                        System.out.println("You could be in need of the food in other rooms.");
                        input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("keep")) {
                            System.out.println("You have kept " + food.getName());
                            player.addToInventory(food);
                        } else if (input.equals("drop")) {
                            dropItem(food);
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } else {
                    System.out.println("This item is not edible.");
                }
                player.removeFromInventory(food);
                if (player.getHealth() <= 0) {
                    String yellowColor = "\033[33m";
                    String resetColor = "\033[0m";
                    System.out.println(yellowColor +
                            "▓██   ██▓ ▒█████   █    ██     ██░ ██  ▄▄▄    ██▒   █▓▓█████    ▓█████▄  ██▓▓█████ ▓█████▄ \n" +
                            " ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██░ ██▒▒████▄ ▓██░   █▒▓█   ▀    ▒██▀ ██▌▓██▒▓█   ▀ ▒██▀ ██▌\n" +
                            "  ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██▀▀██░▒██  ▀█▄▓██  █▒░▒███      ░██   █▌▒██▒▒███   ░██   █▌\n" +
                            "  ░ ▐██▓░▒██   ██░▓▓█  ░██░   ░▓█ ░██ ░██▄▄▄▄██▒██ █░░▒▓█  ▄    ░▓█▄   ▌░██░▒▓█  ▄ ░▓█▄   ▌\n" +
                            "  ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░▓█▒░██▓ ▓█   ▓██▒▒▀█░  ░▒████▒   ░▒████▓ ░██░░▒████▒░▒████▓ \n" +
                            "   ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒     ▒ ░░▒░▒ ▒▒   ▓▒█░░ ▐░  ░░ ▒░ ░    ▒▒▓  ▒ ░▓  ░░ ▒░ ░ ▒▒▓  ▒ \n" +
                            " ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░     ▒ ░▒░ ░  ▒   ▒▒ ░░ ░░   ░ ░  ░    ░ ▒  ▒  ▒ ░ ░ ░  ░ ░ ▒  ▒ \n" +
                            " ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░     ░  ░░ ░  ░   ▒     ░░     ░       ░ ░  ░  ▒ ░   ░    ░ ░  ░ \n" +
                            " ░ ░         ░ ░     ░         ░  ░  ░      ░  ░   ░     ░  ░      ░     ░     ░  ░   ░    \n" +
                            " ░ ░                                              ░              ░                  ░      \n" +
                            resetColor);
                }
            } else {
                System.out.println("You don't have such food in your inventory.");
            }
        }
    }

    private void drink() {
        if (!viewInventory) {
            System.out.println("You have to open your inventory to pick something to drink before drinking.");
        } else {
            Player player = adventure.getPlayer();
            System.out.println("Enter the name or short name of the drink you want to drink:");
            String itemName = scanner.nextLine().trim();
            Item item = player.getItemFromInventory(itemName);
            if (item == null) {
                item = player.getItemFromInventoryByShortName(itemName);
            }
            if (item != null && item instanceof Liquid) {
                Liquid liquid = (Liquid) item;
                int healthChange = liquid.getHealthPoints();
                if (healthChange > 0) {
                    player.increaseHealth(healthChange);
                    System.out.println("You have drinked " + liquid.getName() + " and gained " + healthChange + " health.");
                } else if (healthChange < 0) {
                    System.out.println("This doesn't look healthy. Are you sure you want to drink this? (yes/no)");
                    String input = scanner.nextLine().trim().toLowerCase();
                    if (input.equals("yes")) {
                        player.decreaseHealth(Math.abs(healthChange));
                        System.out.println("You have drinked " + liquid.getName() + " and lost " + Math.abs(healthChange) + " health.");
                    } else if (input.equals("no")) {
                        System.out.println("Would you like to keep or drop this drink? (keep/drop)");
                        System.out.println("You could be in need of the drink in other rooms.");
                        input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("keep")) {
                            System.out.println("You have kept " + liquid.getName());
                            player.addToInventory(liquid);
                        } else if (input.equals("drop")) {
                            dropItem(liquid);
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                    }
                } else {
                    System.out.println("This item is not edible.");
                }
                player.removeFromInventory(liquid);
                if (player.getHealth() <= 0) {
                    String yellowColor = "\033[33m";
                    String resetColor = "\033[0m";
                    System.out.println(yellowColor +
                            "▓██   ██▓ ▒█████   █    ██     ██░ ██  ▄▄▄    ██▒   █▓▓█████    ▓█████▄  ██▓▓█████ ▓█████▄ \n" +
                            " ▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██░ ██▒▒████▄ ▓██░   █▒▓█   ▀    ▒██▀ ██▌▓██▒▓█   ▀ ▒██▀ ██▌\n" +
                            "  ▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██▀▀██░▒██  ▀█▄▓██  █▒░▒███      ░██   █▌▒██▒▒███   ░██   █▌\n" +
                            "  ░ ▐██▓░▒██   ██░▓▓█  ░██░   ░▓█ ░██ ░██▄▄▄▄██▒██ █░░▒▓█  ▄    ░▓█▄   ▌░██░▒▓█  ▄ ░▓█▄   ▌\n" +
                            "  ░ ██▒▓░░ ████▓▒░▒▒█████▓    ░▓█▒░██▓ ▓█   ▓██▒▒▀█░  ░▒████▒   ░▒████▓ ░██░░▒████▒░▒████▓ \n" +
                            "   ██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒     ▒ ░░▒░▒ ▒▒   ▓▒█░░ ▐░  ░░ ▒░ ░    ▒▒▓  ▒ ░▓  ░░ ▒░ ░ ▒▒▓  ▒ \n" +
                            " ▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░     ▒ ░▒░ ░  ▒   ▒▒ ░░ ░░   ░ ░  ░    ░ ▒  ▒  ▒ ░ ░ ░  ░ ░ ▒  ▒ \n" +
                            " ▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░     ░  ░░ ░  ░   ▒     ░░     ░       ░ ░  ░  ▒ ░   ░    ░ ░  ░ \n" +
                            " ░ ░         ░ ░     ░         ░  ░  ░      ░  ░   ░     ░  ░      ░     ░     ░  ░   ░    \n" +
                            " ░ ░                                              ░              ░                  ░      \n" +
                            resetColor);
                }
            } else {
                System.out.println("You don't have such liquid in your inventory.");
            }
        }
    }

    private void dropItem(Item item) {
        Player player = adventure.getPlayer();
        player.getCurrentRoom().addItems(item);
        System.out.println("You have dropped " + item.getName() + ".");
    }

    public boolean isViewInventory() {
        return viewInventory;
    }

    // Method to prompt the user to view their inventory before equipping a weapon
    public String equipWeaponViewInventoryPrompt() {
        return "Please view your inventory before equipping a weapon.";
    }

    public void playerCraftItems() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the item you want to craft: ");
        String name = scanner.nextLine();
        int weight = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Enter the weight of the item: ");
            try {
                weight = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }
        Item newItem = new Item(name, weight);
        adventure.getPlayer().craftItem(newItem);
        System.out.println("You have successfully crafted " + name + ", which weighs: " + weight + " grams.");
    }

    public String promptWeaponSelection() {
        if (!isViewInventory()) {
            return null;
        } else {
            System.out.println("Enter the name of the weapon you want to equip:");
            return scanner.nextLine(); // Return the weapon name entered by the user
        }
    }

    private void viewInventory() {
        viewInventory = true;
        ArrayList<Item> inventory = adventure.getPlayer().getInventoryItems();
        System.out.println("Your inventory: " + formatItemList(inventory));
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
        } else if (currentRoom.getName().equals("Desert")) {
            adventure.getPlayer().saveXyzzyPosition();
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    public String cannotAttackWithWeapon() {
        return "You cannot kill the enemy with the weapons in this room.";
    }

    public String teleportationMessage(String roomName) {
        return "You have teleported back to: " + roomName;
    }

    public String displayDarkRoomMessage() {
        return "It's too dark to see anything. You can't move to other rooms - except the one you came from - until you turn on the lights.";
    }

    public String displayVisitedRoomMessage(String description, String roomName, String shortName) {
        return (description + "You have gone to " + roomName + ", short name: " + shortName);
    }

    public String displayReturnRoomMessage(String roomName) {
        return "You have gone back to " + roomName + ". What now?";
    }

    public String displayHitWallMessage() {
        return "You have hit a wall! Try again.";
    }

    private void displayMenu() {
        String redColor = "\033[31m";
        String resetColor = "\033[0m";

        System.out.println(redColor +
                        " ▄████▄   ██▀███   ▄▄▄      ▒███████▒▓██   ██▓   ▄▄▄█████▓ ▒█████   █     █░███▄    █ \n" +
                        "▒██▀ ▀█  ▓██ ▒ ██▒▒████▄    ▒ ▒ ▒ ▄▀░ ▒██  ██▒   ▓  ██▒ ▓▒▒██▒  ██▒▓█░ █ ░█░██ ▀█   █ \n" +
                        "▒▓█    ▄ ▓██ ░▄█ ▒▒██  ▀█▄  ░ ▒ ▄▀▒░   ▒██ ██░   ▒ ▓██░ ▒░▒██░  ██▒▒█░ █ ░█▓██  ▀█ ██▒\n" +
                        "▒▓▓▄ ▄██▒▒██▀▀█▄  ░██▄▄▄▄██   ▄▀▒   ░  ░ ▐██▓░   ░ ▓██▓ ░ ▒██   ██░░█░ █ ░█▓██▒  ▐▌██▒\n" +
                        "▒ ▓███▀ ░░██▓ ▒██▒ ▓█   ▓██▒▒███████▒  ░ ██▒▓░     ▒██▒ ░ ░ ████▓▒░░░██▒██▓▒██░   ▓██░\n" +
                        "░ ░▒ ▒  ░░ ▒▓ ░▒▓░ ▒▒   ▓▒█░░▒▒ ▓░▒░▒   ██▒▒▒      ▒ ░░   ░ ▒░▒░▒░ ░ ▓░▒ ▒ ░ ▒░   ▒ ▒ \n" +
                        "  ░  ▒     ░▒ ░ ▒░  ▒   ▒▒ ░░░▒ ▒ ░ ▒ ▓██ ░▒░        ░      ░ ▒ ▒░   ▒ ░ ░ ░ ░░   ░ ▒░\n" +
                        "░          ░░   ░   ░   ▒   ░ ░ ░ ░ ░ ▒ ▒ ░░       ░      ░ ░ ░ ▒    ░   ░    ░   ░ ░ \n" +
                        "░ ░         ░           ░  ░  ░ ░     ░ ░                     ░ ░      ░            ░ \n" +
                        "░                           ░         ░ ░                                             \n" +
                        resetColor);
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

    private void printAvailableDirections() {
        ArrayList<Direction> availableDirections = new ArrayList<>();
        System.out.println("You also see all the available openings in this room: ");
        for (Direction direction : Direction.values()) {
            if (currentRoom.getNeighbor(direction) != null) {
                availableDirections.add(direction);
            }
        }
        System.out.println(formatDirectionList(availableDirections));
    }

    private String formatDirectionList(ArrayList<Direction> directions) {
        if (directions.isEmpty()) {
            return "There are no available openings.";
        } else if (directions.size() == 1) {
            return capitalizeFirstLetter(directions.get(0).toString().toLowerCase());
        } else if (directions.size() == 2) {
            return capitalizeFirstLetter(directions.get(0).toString().toLowerCase()) + " and " + capitalizeFirstLetter(directions.get(1).toString().toLowerCase());
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < directions.size() - 1; i++) {
                result.append(capitalizeFirstLetter(directions.get(i).toString().toLowerCase())).append(", ");
            }
            result.append("and ").append(capitalizeFirstLetter(directions.get(directions.size() - 1).toString().toLowerCase()));
            return result.toString();
        }
    }

    private String capitalizeFirstLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    private void printRoomItems() {
        System.out.println("You look around the room. You see:");
        ArrayList<Item> items = currentRoom.getItems();
        if (items.isEmpty()) {
            System.out.println("There are no items in this room.");
        } else {
            System.out.println(formatItemList(items));
        }
    }

    private String formatItemList(ArrayList<Item> items) {
        if (items.isEmpty()) {
            return "Your inventory is empty.";
        } else if (items.size() == 1) {
            return items.get(0).getName();
        } else if (items.size() == 2) {
            return items.get(0).getName() + " and " + items.get(1).getName();
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < items.size() - 1; i++) {
                result.append(items.get(i).getName()).append(", ");
            }
            result.append("and ").append(items.get(items.size() - 1).getName());
            return result.toString();
        }
    }

    public void enemyAttacked(String enemyName, int damageDealt, int playerHealthBefore, int playerHealthAfter) {
        System.out.println("The enemy " + enemyName + " attacked you and dealt " + damageDealt + " damage.");
        System.out.println("Your health decreased from " + playerHealthBefore + " to " + playerHealthAfter);
    }

    public void defeatedEnemy(String enemyName) {
        System.out.println("You have defeated " + enemyName + "!");
    }

    public void weaponAlreadyEquippedMessage() {
        System.out.println("The weapon is already equipped.");
    }

    public void weaponEquippedMessage(String weaponName) {
        System.out.println("You have equipped " + weaponName + ".");
    }

    public void weaponNotInInventoryMessage() {
        System.out.println("You don't have such weapon in your inventory.");
    }

    public void weaponNotEquipped() {
        System.out.println("You don't have a weapon equipped.");
    }

    public void weaponNoAmmunition(String weaponName) {
        System.out.println("Your " + weaponName + " has no more ammunition left.");
    }

    public void weaponNoEnemies() {
        System.out.println("There are no enemies in this room to attack.");
    }

    public void checkGameOver() {
        Player player = adventure.getPlayer();
        Room currentRoom = player.getCurrentRoom();

        if (player.getHealth() <= 0) {
            gameOver();
        }

        if (currentRoom.getEnemies().isEmpty()) {
            victory();
        }
    }

    public void victory() {
        System.out.println("Congratulations! You have defeated all enemies. You win!");
        System.exit(0);
    }


    public void gameOver() {
        String redColor = "\033[31m";
        String resetColor = "\033[0m";

        System.out.println(redColor +
                "  ▄████  ▄▄▄       ███▄ ▄███▓▓█████     ▒█████   ██▒   █▓▓█████  ██▀███  \n" +
                " ██▒ ▀█▒▒████▄    ▓██▒▀█▀ ██▒▓█   ▀    ▒██▒  ██▒▓██░   █▒▓█   ▀ ▓██ ▒ ██▒\n" +
                "▒██░▄▄▄░▒██  ▀█▄  ▓██    ▓██░▒███      ▒██░  ██▒ ▓██  █▒░▒███   ▓██ ░▄█ ▒\n" +
                "░▓█  ██▓░██▄▄▄▄██ ▒██    ▒██ ▒▓█  ▄    ▒██   ██░  ▒██ █░░▒▓█  ▄ ▒██▀▀█▄  \n" +
                "░▒▓███▀▒ ▓█   ▓██▒▒██▒   ░██▒░▒████▒   ░ ████▓▒░   ▒▀█░  ░▒████▒░██▓ ▒██▒\n" +
                " ░▒   ▒  ▒▒   ▓▒█░░ ▒░   ░  ░░░ ▒░ ░   ░ ▒░▒░▒░    ░ ▐░  ░░ ▒░ ░░ ▒▓ ░▒▓░\n" +
                "  ░   ░   ▒   ▒▒ ░░  ░      ░ ░ ░  ░     ░ ▒ ▒░    ░ ░░   ░ ░  ░  ░▒ ░ ▒░\n" +
                "░ ░   ░   ░   ▒   ░      ░      ░      ░ ░ ░ ▒       ░░     ░     ░░   ░ \n" +
                "      ░       ░  ░       ░      ░  ░       ░ ░        ░     ░  ░   ░     \n" +
                "                                                     ░                   \n" +
                resetColor);
        System.exit(0);
    }
}