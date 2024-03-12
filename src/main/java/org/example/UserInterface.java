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
        this.helpDisplayed = false; //i starten har du ikke tastet help
        this.choiceEntered = false; //i starten har du ikke indtastet noget
        this.lookDisplayed = false; //i starten har du ikke indtastet look
    }

    public void startProgram() {
        String choice;

        do {
            if (!helpDisplayed && !choiceEntered) { //er helpDisplayed og choiceEntered false i starten, så skal menuen vises. Ellers ikke.
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
                    adventure.getPlayer().unlockWestRoom(); //når du taster "unlock" bliver denne her triggered
                    break;
                case "look":
                    lookDisplayed = true; //når du taster look, så viser den ikke længere menuen. For at få vist menuen, så skal du skrive help.
                    for (Item item : adventure.lookAround()) { //metoden, som viser hvilke items er i rummet
                        System.out.println(item.getName());
                    }
                    break;
                case "help":
                    System.out.println(adventure.getPlayer().getCurrentRoom().helpUser(commands()));
                    helpDisplayed = true; //når du har tastet help, så viser den ikke længere menuen som den plejer.
                    break;
                case "take":
                    if (!lookDisplayed) { //hvis du ikke har "looked" så kan du ikke tage noget fra rummet (du kan tage noget, du ikke ved er der)
                        System.out.println("You need to look around before taking items.");
                        break;
                    }
                    ArrayList<Item> roomItems = adventure.lookAround(); //den siger, at du har "looked"
                    if (!roomItems.isEmpty()) { //hvis der stadig er items i rummet, kan du stadig samle op
                        System.out.println("Enter the name or short name of the item(s) you want to take (comma-separated):");
                        String itemsToTakeInput = scanner.nextLine().trim().toLowerCase(); //metode som gør alle bogstaver til lower case
                        String[] itemsToTake = itemsToTakeInput.split(","); //sætter et komma mellem navn og andet tekst
                        ArrayList<Item> takenItems = new ArrayList<>();
                        for (String itemName : itemsToTake) {
                            boolean itemFound = false;
                            for (Item item : roomItems) {
                                if (item.getName().equalsIgnoreCase(itemName.trim())) {
                                    itemFound = true;
                                    takenItems.add(item);
                                    adventure.takeItemFromRoom(item.getName()); //samler item op
                                    adventure.addToInventory(item); //tilføjet til inventory
                                    break;
                                } else if (item.getShortName().equalsIgnoreCase(itemName.trim())) {
                                    itemFound = true;
                                    Item takenItem = adventure.takeItemFromRoomByShortName(itemName.trim()); //metode som tillader short names
                                    if (takenItem != null) {
                                        takenItems.add(takenItem);
                                        adventure.addToInventory(takenItem);
                                    } else {
                                        System.out.println("Item '" + itemName.trim() + "' not found in the room."); //hvis du taster et ikke ekisterende item, så får du en fejlbesked
                                    }
                                    break;
                                }
                            }
                            if (!itemFound) {
                                System.out.println("Item '" + itemName.trim() + "' not found in the room."); //hvis intet er i rummet, får du en fejlbesked
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
                        String[] itemsToDrop = itemsToDropInput.split(","); //sætter komma mellem det du har droppet og andet tekst
                        ArrayList<Item> droppedItems = new ArrayList<>();
                        for (String itemName : itemsToDrop) {
                            Item item = adventure.dropItemFromInventory(itemName.trim());
                            if (item == null) {
                                item = adventure.dropItemFromInventoryByShortName(itemName.trim()); //metode som tillader short names
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
                        System.out.println("Your inventory is empty."); //når du ikke har mere at droppe, så får du en fejlbesked
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