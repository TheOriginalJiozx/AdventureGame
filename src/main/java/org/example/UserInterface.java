package org.example;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    MapConnections mapConnections = new MapConnections();
    private Adventure adventure;
    private boolean helpDisplayed;
    private boolean choiceEntered;
    private boolean lookDisplayed;
    private boolean viewInventory;
    private boolean playNowEntered = false;
    private Room currentRoom;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.adventure = new Adventure();
        adventure.currentRoom = adventure.player.getCurrentRoom();
        this.helpDisplayed = false;
        this.choiceEntered = false;
        this.lookDisplayed = false;
        this.viewInventory = false;
    }

    public void startProgram() {
        String choice;
        Scanner scanner = new Scanner(System.in);
        do {
            if (!playNowEntered) {
                System.out.println("Welcome to the Crazy Town. Please enter 'Play Now' to begin:");
                choice = scanner.nextLine().trim().toLowerCase();
                if (choice.equals("play now")) {
                    playNowEntered = true;
                    initializeGame();
                } else {
                    System.out.println("Please enter 'Play Now' to begin.");
                    continue;
                }
            } else if (!helpDisplayed && !choiceEntered) {
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
                            String unlockMessage = adventure.unlockNorthRoom();
                            System.out.println(unlockMessage);
                        } else {
                            System.out.println("You chose not to unlock the north room.");
                        }
                    } else {
                        adventure.currentRoom.tryDirection(Direction.NORTH);
                        adventure.currentRoom = adventure.go(Direction.NORTH);
                        Room currentRoom = adventure.currentRoom;
                        if (currentRoom.getName().equalsIgnoreCase("King David's Room")) {
                            mapConnections.unlockKingsRoom();
                        } else if (currentRoom.getName().equalsIgnoreCase("Coast")) {
                            mapConnections.unlockCoastN();
                        }
                    }
                    break;
                case "go south":
                case "south":
                    if (adventure.tryUnlockSouthRoom()) {
                        System.out.println("The south room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock.");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            String unlockMessage = adventure.unlockSouthRoom();
                            System.out.println(unlockMessage);
                        } else {
                            System.out.println("You chose not to unlock the south room.");
                        }
                    } else {
                        adventure.currentRoom.tryDirection(Direction.SOUTH);
                        adventure.currentRoom = adventure.go(Direction.SOUTH);
                        Room currentRoom = adventure.currentRoom;
                        if (currentRoom.getName().equalsIgnoreCase("Eden's Garden")) {
                            mapConnections.unlockEdensGardenS();
                        }
                    }
                    break;
                case "go east":
                case "e":
                    if (adventure.tryUnlockEastRoom()) {
                        System.out.println("The east room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock.");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            String unlockMessage = adventure.unlockEastRoom();
                            System.out.println(unlockMessage);
                        } else {
                            System.out.println("You chose not to unlock the east room.");
                        }
                    } else {
                        adventure.currentRoom.tryDirection(Direction.EAST);
                        adventure.currentRoom = adventure.go(Direction.EAST);
                        Room currentRoom = adventure.currentRoom;
                        if (currentRoom.getName().equalsIgnoreCase("Eden's Garden")) {
                            mapConnections.unlockEdensGardenE();
                        } else if (currentRoom.getName().equalsIgnoreCase("Coast")) {
                            mapConnections.unlockCoastE();
                        } else if (currentRoom.getName().equalsIgnoreCase("Clown Town")) {
                            mapConnections.unlockClownTownE();
                        } else if (currentRoom.getName().equalsIgnoreCase("Vice City")) {
                            mapConnections.unlockViceCityE();
                        }
                    }
                    break;
                case "go west":
                case "w":
                    if (adventure.tryUnlockWestRoom()) {
                        System.out.println("The west room is locked. Enter 'unlock' to unlock it, or press Enter to choose not to unlock.");
                        String input = scanner.nextLine().trim().toLowerCase();
                        if (input.equals("unlock")) {
                            String unlockMessage = adventure.unlockWestRoom();
                            System.out.println(unlockMessage);
                        } else {
                            System.out.println("You chose not to unlock the west room.");
                        }
                    } else {
                        adventure.currentRoom.tryDirection(Direction.WEST);
                        adventure.currentRoom = adventure.go(Direction.WEST);
                        Room currentRoom = adventure.currentRoom;
                        if (adventure.currentRoom.getName().equalsIgnoreCase("Coast")) {
                            mapConnections.unlockCoastW();
                        } else if (adventure.currentRoom.getName().equalsIgnoreCase("Bomb Town")) {
                            mapConnections.unlockBombTownW();
                        }
                    }
                    break;
                case "unlock":
                    adventure.handleUnlockRoom();
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
                    adventure.player.takeItem(this);
                    break;
                case "drop":
                case "d":
                    adventure.player.dropItem(this);
                    break;
                case "craft":
                case "c":
                    adventure.player.playerCraftItems(this);
                    break;
                case "eat":
                    adventure.player.eat(this);
                    break;
                case "drink":
                    adventure.player.drink(this);
                break;
                case "equip":
                case "eq":
                    String weaponName = promptWeaponSelection();
                    adventure.player.equipWeapon(weaponName, this);
                    break;
                case "attack":
                case "att":
                    String attackChoice = getUserInputForAttack();
                    switch (attackChoice.toLowerCase()) {
                        case "enemy":
                            adventure.player.useWeapon();
                            break;
                        case "npc":
                            adventure.player.useWeaponNPC();
                            break;
                        case "thief":
                            adventure.player.useWeaponThief();
                            break;
                        default:
                            System.out.println("Invalid choice. Please select 'enemy', 'npc' or 'thief'.");
                    }
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
                    adventure.handleXyzzy();
                    break;
                case "turn on":
                case "on":
                    if (adventure.tryTurnOnLights()) {
                        System.out.println(adventure.turnOnLightsRoom3());
                    } else {
                        Room currentRoom = adventure.player.getCurrentRoom();
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
                        Room currentRoom = adventure.player.getCurrentRoom();
                        if (currentRoom.getName().equals("Mine Tunnels")) {
                            System.out.println("The lights are already off.");
                        } else {
                            System.out.println("You can't turn off the lights here.");
                        }
                    }
                    break;
                case "mute":
                    if (adventure.currentRoom != null && adventure.currentRoom.music != null) {
                        adventure.currentRoom.music.stopMusic();
                    }
                    break;
                case "resume":
                    adventure.currentRoom.music.playMusic();
                    break;
                case "map":
                    mapConnections.displayMap();
                    break;
                case "teleport":
                    System.out.println("Enter the name of the room you want to teleport to: ");
                    teleportRooms();
                    break;
                default:
                    if (!playNowEntered) {
                        System.out.println("Please enter 'Play Now' to begin.");
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
            }
        } while (!choice.equalsIgnoreCase("exit"));
    }

    private void initializeGame() {
        currentRoom = null;
        System.out.println("Game initialized. Get ready to explore!");
        adventure.handleResumeCommand();
        displayMenu();
    }

    public void teleportRooms() {
        String roomName = scanner.nextLine().trim().toLowerCase();
        switch (roomName) {
            case "bomb town":
                teleportToRoom(roomName, mapConnections.isBombTownUnlockedW());
                break;
            case "coast":
                teleportToRoom(roomName, mapConnections.isCoastUnlockedW() && mapConnections.isCoastUnlockedE() && mapConnections.isCoastUnlockedN());
                break;
            case "king david's room":
                teleportToRoom(roomName, mapConnections.isKingsRoomUnlocked());
                break;
            case "vice city":
                teleportToRoom(roomName, mapConnections.isViceCityUnlockedE());
                break;
            case "eden's garden":
                teleportToRoom(roomName, mapConnections.isEdensGardenUnlockedE() && mapConnections.isEdensGardenUnlockedS());
                break;
            case "clown town":
                teleportToRoom(roomName, mapConnections.isClownTownUnlockedE());
                break;
            default:
                System.out.println("The specified room does not exist.");
        }
    }

    private void teleportToRoom(String roomName, boolean isUnlocked) {
        if (isUnlocked) {
            if (adventure.currentRoom != null && adventure.currentRoom.music != null) {
                adventure.currentRoom.music.stopMusic();
                System.out.println("Music stopped before teleporting.");
            }
            String teleportationMessage = adventure.handleTeleportation(roomName);
            System.out.println(teleportationMessage);
            if (adventure.currentRoom != null && adventure.currentRoom.music != null) {
                adventure.currentRoom.music.playMusic();
            }
        } else {
            System.out.println("You cannot teleport to " + roomName + " because it is locked for teleporters till they unlock the room from all directions.");
        }
    }

    private String getUserInputForAttack() {
        System.out.println("Would you like to attack an enemy or a friendly NPC?");
        System.out.println("Enter 'enemy' to attack an enemy");
        System.out.println("Enter 'npc' to attack a friendly NPC");
        System.out.println("Enter 'thief' to attack thief");
        return scanner.nextLine().trim();
    }

    private void health() {
        Player player = adventure.getPlayer();
        int health = player.getHealth();
        System.out.println("Your current health points: " + health);
    }

    private void lookAround() {
        lookDisplayed = true;
        if (adventure.currentRoom.allDirectionsTried()) {
            printRoomItems();
            printAvailableDirections();
        } else {
            printRoomItems();
            printAllEnemies();
            printAllNPCs();
            printAllThieves();
        }
    }

    public void roomsLocked(String lockedRooms) {
        System.out.println("The following rooms are locked: " + lockedRooms + ". You must go in a locked room's direction to unlock the room.");
    }

    public void adjecentRoomsUnlocked() {
       System.out.println("All adjacent rooms are already unlocked.");
    }

    public boolean isLookDisplayed() {
        return lookDisplayed;
    }

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

    public void maxWeightPrompt() {
        System.out.println("You cannot pick up this item as it would make your inventory exceed the weight limit.");
    }

    public void takenItemWarningPrompt(Item item) {
        System.out.println("You have taken " + item.getName() + ", short name: " + item.getShortName() + ". It weighs: " + item.getWeight() + " grams.");
        System.out.println("You cannot pick up more items until you drop something from your inventory.");
    }

    public void takenItemPrompt(Item item) {
        if (item instanceof RangedWeapon) {
            RangedWeapon rangedWeapon = (RangedWeapon) item;
            System.out.println("You have taken " + rangedWeapon.getName() + ", short name: " + rangedWeapon.getShortName() + ". It weighs: " + rangedWeapon.getWeight() + " grams," + " and it has " + rangedWeapon.getAmmonition() + " ammo left.");
        } else {
            System.out.println("You have taken " + item.getName() + ", short name: " + item.getShortName() + ". It weighs: " + item.getWeight() + " grams.");
        }
    }

    public Item promptDropItemName() {
        System.out.println("Enter the name or short name of the item you want to drop: ");
        String itemName = scanner.nextLine().trim().toLowerCase();
        Item item = adventure.dropItemFromInventory(itemName);
        if (item == null) {
            item = adventure.dropItemFromInventoryByShortName(itemName);
            if (item == null) {
                System.out.println("The item \"" + itemName + "\" does not exist in your inventory.");
            }
        }
        return item;
    }

    public void dropItemViewInventoryPrompt() {
        System.out.println("You have to view your inventory before dropping an item.");
    }

    public void droppedItemPrompt(Item item) {
        System.out.println("You have dropped " + item.getName() + ".");
    }

    public void droppedItemNotFound() {
        System.out.println("You don't have such item in your inventory.");
    }

    public void eatOrDrinkInvalidInputKeepOrDrop() {
        System.out.println("Invalid input. Please enter 'keep' or 'drop'.");
    }

    public void eatOrDrinkInvalidInputYesOrNo() {
        System.out.println("Invalid input. Please enter 'yes' or 'no'.");
    }

    public void itemNotEdible() {
        System.out.println("This item is not edible.");
    }

    public String promptFoodName() {
        System.out.println("Enter the name or short name of the food you want to eat:");
        return scanner.nextLine().trim();
    }

    public void eatViewInventoryPrompt() {
        System.out.println("You have to open your inventory to pick something to eat before eating.");
    }

    public String confirmEatingUnhealthyFood() {
        System.out.println("This doesn't look healthy. Are you sure you want to eat this? (yes/no)");
        return scanner.nextLine().trim().toLowerCase();
    }

    public String keepOrDropFoodPrompt() {
        System.out.println("Would you like to keep or drop this food? (keep/drop)");
        System.out.println("You could be in need of the food in other rooms.");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void healthChange(int healthChange, Food food) {
        adventure.player.increaseHealth(healthChange);
        System.out.println("You have eaten " + food.getName() + " and gained " + healthChange + " health.");
    }

    public void healthDecreaseChange(int healthChange, Food food) {
        adventure.player.decreaseHealth(Math.abs(healthChange));
        System.out.println("You have eaten " + food.getName() + " and lost " + Math.abs(healthChange) + " health.");
    }

    public void keepFoodPrompt(Food food) {
        System.out.println("You have kept " + food.getName());
        adventure.player.addToInventory(food);
    }

    public void foodNotFound() {
        System.out.println("You don't have such food in your inventory.");
    }

    public void foodGameOver() {
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
        System.exit(0);
    }
    public String promptLiquidName() {
        System.out.println("Enter the name or short name of the liquid you want to drink:");
        return scanner.nextLine().trim();
    }

    public void liquidViewInventoryPrompt() {
        System.out.println("You have to open your inventory to pick something to drink before drinking.");
    }

    public String confirmDrinkingUnhealthyLiquid() {
        System.out.println("This doesn't look healthy. Are you sure you want to eat this? (yes/no)");
        return scanner.nextLine().trim().toLowerCase();
    }

    public String keepOrDropLiquidPrompt() {
        System.out.println("Would you like to keep or drop this food? (keep/drop)");
        System.out.println("You could be in need of the food in other rooms.");
        return scanner.nextLine().trim().toLowerCase();
    }

    public void healthChange(int healthChange, Liquid liquid) {
        adventure.player.increaseHealth(healthChange);
        System.out.println("You have drinked " + liquid.getName() + " and gained " + healthChange + " health.");
    }

    public void healthDecreaseChange(int healthChange, Liquid liquid) {
        adventure.player.decreaseHealth(Math.abs(healthChange));
        System.out.println("You have eaten " + liquid.getName() + " and lost " + Math.abs(healthChange) + " health.");
    }

    public void keepLiquidPrompt(Liquid liquid) {
        System.out.println("You have kept " + liquid.getName());
        adventure.player.addToInventory(liquid);
    }

    public void liquidNotFound() {
        System.out.println("You don't have such liquid in your inventory.");
    }

    public void liquidGameOver() {
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
        System.exit(0);
    }

    public boolean isViewInventory() {
        return viewInventory;
    }

    public void equipWeaponViewInventoryPrompt() {
        System.out.println("Please view your inventory before equipping a weapon.");
    }

    public void displayCraftingMessage(String itemName, int weight) {
        System.out.println("You have successfully crafted " + itemName + ", which weighs: " + weight + " grams.");
    }

    public String promptWeaponSelection() {
        if (!isViewInventory()) {
            return null;
        } else {
            System.out.println("Enter the name of the weapon you want to equip:");
            return scanner.nextLine();
        }
    }

    private void viewInventory() {
        viewInventory = true;
        ArrayList<Item> inventory = adventure.getPlayer().getInventoryItems();
        System.out.println("Your inventory: " + formatItemList(inventory));
    }

    public void teleportationMessage(String roomName) {
        System.out.println("You have teleported back to: " + roomName);
    }

    public void displayDarkRoomMessage() {
        System.out.println("It's too dark to see anything. You can't move to other rooms - except the one you came from - until you turn on the lights.");
    }

    public void displayVisitedRoomMessage(String description, String roomName, String shortName) {
        System.out.println(description + "You have gone to " + roomName + ", short name: " + shortName);
    }

    public void displayReturnRoomMessage(String roomName) {
        System.out.println("You have gone back to " + roomName + ". What now?");
    }

    public void displayHitWallMessage() {
        System.out.println("You have hit a wall! Try again.");
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

        System.out.println("You wake up completely lost and totally confused. You have no memory of who you are, who you were or where you are. You feel frightened, restless and excited for the amazing adventure that lies ahead. You have a note written in blood  in your hand:\n" +
                "YOU HAVE TO FIND 5 COMPONENTS TO CRAFT THE ZEUS DESTROYER IN ORDER TO KILL ZEUS AND TAKE HIS PLACE AMONGST THE GODS. You are in " + adventure.currentRoom.getName() + ". What would you like to do?");
        System.out.println("Enter 'go north' (n) to go north");
        System.out.println("Enter 'go south' (s) to go south");
        System.out.println("Enter 'go east' (e) to go east");
        System.out.println("Enter 'go west' (w) to go west");
        System.out.println("Enter 'look' (l) to look around");
        System.out.println("Enter 'take' (t) to take something");
        System.out.println("Enter 'drop' (d) to drop something");
        System.out.println("Enter 'eat' to eat food");
        System.out.println("Enter 'equip' (eq) to equip a weapon");
        System.out.println("Enter 'attack' (att) to attack NPC or Enemy");
        System.out.println("Enter 'health' (hp) to view health");
        System.out.println("Enter 'inventory' (inv) to view your inventory");
        System.out.println("Enter 'help' (h) to display this menu again");
        System.out.println("Enter 'map' (m) to open map");
        System.out.println("Enter 'xyzzy' to teleport to previous room you teleported from (Desert as initial)");
        System.out.println("Enter 'teleport' to teleport to any room");
        System.out.println("Enter 'turn on' (on) to turn on lights in a room where the lights are off (if possible)");
        System.out.println("Enter 'turn off' (off) to turn off lights in a room where the lights are on (if possible)");
        System.out.println("Enter 'mute' to mute music");
        System.out.println("Enter 'resume' to resume music");
        System.out.println("Enter 'exit' (ex) to quit the game");
        choiceEntered = false;
    }

    public String commands() {
        StringBuilder commandList = new StringBuilder();
        commandList.append("'go north or 'n' to go north\n");
        commandList.append("'go south or 's' to go south\n");
        commandList.append("'go east or 'e' to go east\n");
        commandList.append("'go west or 'w' to go west\n");
        commandList.append("'look' or 'l' to look around\n");
        commandList.append("'eat' to eat\n");
        commandList.append("'health' or 'hp' to view health\n");
        commandList.append("'take' or 't' to pick up something\n");
        commandList.append("'equip' or 'eq' to equip a weapon\n");
        commandList.append("'drop' or 'd' to drop something\n");
        commandList.append("'health' or 'hp' to view your health points\n");
        commandList.append("'help' or 'h' if you forgot which room you are in\n");
        commandList.append("'inventory' or 'inv' to open your inventory\n");
        commandList.append("'map' or 'm' to open map\n");
        commandList.append("'xyzzy' to teleport to previous room you teleported from (room 1 as initial)\n");
        commandList.append("'teleport' to teleport to any room\n");
        commandList.append("'turn on' to turn on lights in a room\n");
        commandList.append("'turn off' to turn off lights in a room\n");
        commandList.append("'mute' to mute music\n");
        commandList.append("'resume' to resume music\n");
        commandList.append("'exit' or 'ex' to exit program\n");
        return commandList.toString();
    }

    public String helpUser(String commands) {
        StringBuilder helpMessage = new StringBuilder();
        Room currentRoom = adventure.getPlayer().getCurrentRoom();
        helpMessage.append("You are in room: ").append(currentRoom.getName()).append("\n");
        helpMessage.append("Description: ").append(currentRoom.getDescription()).append("\n");
        helpMessage.append("Available commands:\n");
        helpMessage.append(commands);
        String lightsStatus = currentRoom.areLightsOff() ? "off" : "on";
        helpMessage.append("The lights in the room are: ").append(lightsStatus).append("\n");
        return helpMessage.toString();
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
            if (adventure.currentRoom.getNeighbor(direction) != null) {
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
        ArrayList<Item> items = adventure.currentRoom.getItems();
        if (items.isEmpty()) {
            System.out.println("There are no items in this room.");
        } else {
            System.out.println(formatItemList(items));
        }
    }

    private void printAllEnemies() {
        System.out.println("\nYou look also see the following enemies:");
        ArrayList<Enemy> enemies = adventure.currentRoom.getEnemies();
        if (enemies.isEmpty()) {
            System.out.println("There are no enemies in this room.");
        } else {
            System.out.println(formatEnemiesList(enemies));
        }
    }

    private void printAllNPCs() {
        System.out.println("\nAnd these friendly NPCs:");
        ArrayList<NPC> npcs = adventure.currentRoom.getNPCs();
        if (npcs.isEmpty()) {
            System.out.println("There are no NPCs in this room.");
        } else {
            System.out.println(formatNPCList(npcs));
        }
    }

    private void printAllThieves() {
        System.out.println("\nAnd these thieves:");
        ArrayList<Thief> thieves = adventure.currentRoom.getThieves();
        if (thieves.isEmpty()) {
            System.out.println("There are no thieves in this room.");
        } else {
            System.out.println(formatThiefList(thieves));
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

    private String formatEnemiesList(ArrayList<Enemy> enemies) {
        if (enemies.isEmpty()) {
            return "There are no enemies in this room";
        } else if (enemies.size() == 1) {
            return enemies.get(0).getName();
        } else if (enemies.size() == 2) {
            return enemies.get(0).getName() + " and " + enemies.get(1).getName();
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < enemies.size() - 1; i++) {
                result.append(enemies.get(i).getName()).append(", ");
            }
            result.append("and ").append(enemies.get(enemies.size() - 1).getName());
            return result.toString();
        }
    }

    private String formatNPCList(ArrayList<NPC> npcs) {
        if (npcs.isEmpty()) {
            return "There are no NPCs in this room";
        } else if (npcs.size() == 1) {
            return npcs.get(0).getName();
        } else if (npcs.size() == 2) {
            return npcs.get(0).getName() + " and " + npcs.get(1).getName();
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < npcs.size() - 1; i++) {
                result.append(npcs.get(i).getName()).append(", ");
            }
            result.append("and ").append(npcs.get(npcs.size() - 1).getName());
            return result.toString();
        }
    }

    private String formatThiefList(ArrayList<Thief> thieves) {
        if (thieves.isEmpty()) {
            return "There are no thieves in this room";
        } else if (thieves.size() == 1) {
            return thieves.get(0).getName();
        } else if (thieves.size() == 2) {
            return thieves.get(0).getName() + " and " + thieves.get(1).getName();
        } else {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < thieves.size() - 1; i++) {
                result.append(thieves.get(i).getName()).append(", ");
            }
            result.append("and ").append(thieves.get(thieves.size() - 1).getName());
            return result.toString();
        }
    }

    public void enemyAttacked(String enemyName, int damageDealt, int playerHealthBefore, int playerHealthAfter) {
        System.out.println("The enemy " + enemyName + " attacked you and dealt " + damageDealt + " damage.");
        System.out.println("Your health decreased from " + playerHealthBefore + " to " + playerHealthAfter);
    }

    public void playerAttack(String enemyName, int damageDealt, int enemyHealthAfter) {
        System.out.println("You hit the enemy " + enemyName + " attacked you and dealt " + damageDealt + " damage. ");
        System.out.println(enemyName + " has " + enemyHealthAfter + " HP left.");
    }

    public void defeatedEnemy(String enemyName) {
        System.out.println("You have defeated " + enemyName + "!");
    }

    public void NPCAttacked(String NPCName, int damageDealt, int playerHealthBefore, int playerHealthAfter) {
        System.out.println("The NPC " + NPCName + " attacked you and dealt " + damageDealt + " damage.");
        System.out.println("Your health decreased from " + playerHealthBefore + " to " + playerHealthAfter);
    }

    public void PlayerNPCAttacked(String NPCName, int damageDealt, int npcHealthAfter) {
        System.out.println("You attacked the NPC " + NPCName + " attacked you and dealt " + damageDealt + " damage. ");
        System.out.println(NPCName + " has " + npcHealthAfter + " HP left.");
    }

    public void ThiefAttacked(String ThiefName, int damageDealt, int playerHealthBefore, int playerHealthAfter) {
        System.out.println("The thief " + ThiefName + " attacked you and dealt " + damageDealt + " damage.");
        System.out.println("Your health decreased from " + playerHealthBefore + " to " + playerHealthAfter);
    }

    public void PlayerThiefAttacked(String ThiefName, int damageDealt, int thiefHealthAfter) {
        System.out.println("You attacked the theif " + ThiefName + " attacked you and dealt " + damageDealt + " damage. ");
        System.out.println(ThiefName + " has " + thiefHealthAfter + " HP left.");
    }

    public void defeatedThief(String thiefName) {
        System.out.println("You have defeated " + thiefName + "!");
    }

    public void noNPCsInRoom() {
        System.out.println("There are no NPCs in this room to attack.");
    }

    public void noThievesInRoom() {
        System.out.println("There are no thieves in this room to attack.");
    }

    public void defeatedNPC(String NPCName) {
        System.out.println("You have defeated " + NPCName + "!");
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

    public void noEnemiesInRoom() {
        System.out.println("There are no enemies in this room to attack.");
    }

    public void victory() {
        String yellowColor = "\033[33m";
        String resetColor = "\033[0m";
        System.out.println(yellowColor +

                "▓██   ██▓ ▒█████   █    ██     ██░ ██  ▄▄▄    ██▒   █▓▓█████     █     █░ ▒█████   ███▄    █\n" +
                "▒██  ██▒▒██▒  ██▒ ██  ▓██▒   ▓██░ ██▒▒████▄ ▓██░   █▒▓█   ▀    ▓█░ █ ░█░▒██▒  ██▒ ██ ▀█   █\n" +
                "▒██ ██░▒██░  ██▒▓██  ▒██░   ▒██▀▀██░▒██  ▀█▄▓██  █▒░▒███      ▒█░ █ ░█ ▒██░  ██▒▓██  ▀█ ██▒\n" +
                "░ ▐██▓░▒██   ██░▓▓█  ░██░   ░▓█ ░██ ░██▄▄▄▄██▒██ █░░▒▓█  ▄    ░█░ █ ░█ ▒██   ██░▓██▒  ▐▌██▒\n" +
                "░ ██▒▓░░ ████▓▒░▒▒█████▓    ░▓█▒░██▓ ▓█   ▓██▒▒▀█░  ░▒████▒   ░░██▒██▓ ░ ████▓▒░▒██░   ▓██░\n" +
                "██▒▒▒ ░ ▒░▒░▒░ ░▒▓▒ ▒ ▒     ▒ ░░▒░▒ ▒▒   ▓▒█░░ ▐░  ░░ ▒░ ░   ░ ▓░▒ ▒  ░ ▒░▒░▒░ ░ ▒░   ▒ ▒\n" +
                "▓██ ░▒░   ░ ▒ ▒░ ░░▒░ ░ ░     ▒ ░▒░ ░  ▒   ▒▒ ░░ ░░   ░ ░  ░     ▒ ░ ░    ░ ▒ ▒░ ░ ░░   ░ ▒░\n" +
                "▒ ▒ ░░  ░ ░ ░ ▒   ░░░ ░ ░     ░  ░░ ░  ░   ▒     ░░     ░        ░   ░  ░ ░ ░ ▒     ░   ░ ░\n" +
                "░ ░         ░ ░     ░         ░  ░  ░      ░  ░   ░     ░  ░       ░        ░ ░           ░\n" +
                "░ ░                                              ░\n" +

                resetColor);
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