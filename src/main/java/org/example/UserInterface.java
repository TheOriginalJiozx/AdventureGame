package org.example;

import java.util.Scanner;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    private boolean running = true;
    private final Adventure adventure;

    public UserInterface(Adventure adventure) {
        this.adventure = adventure;
    }

    public void startProgram() {
        System.out.println("You wake up completely lost and totally confused. You have no memory of who you are, who you were or where you are.\n" +
                "You feel frightened, restless and excited for the amazing adventure that lies ahead. You have a note in your hand:\n" +
                "YOU MUST FIND THE DOOR WITH THE ROOM BEHIND FILLED WITH EVERYTHING YOU HAVE EVER DESIRED\n" +
                "\n" +
                "You begin to figure out in which direction you will travel.");


        while (running) {

            displayMenu();
            String choice = input.nextLine().toLowerCase();
            processChoice(choice);
        }
        input.close();
    }

    private void displayMenu() {
        System.out.println("\nMenu");
        System.out.println("Enter 'help' to get help");
        System.out.println("Enter 'inventory' or 'inv' to see what is in your inventory");
        System.out.println("Enter 'look around' or 'look' to look around)");
        System.out.println("Enter 'take' to take item(s)");
        System.out.println("Enter 'drop' or 'd' to drop item(s)");
        System.out.println("Enter 'go north' or 'n' to go north");
        System.out.println("Enter 'go south' or 's' to go south");
        System.out.println("Enter 'go west' or 'w' to go west");
        System.out.println("Enter 'go east' or 'e' to go east");
        System.out.println("Enter 'exit' to exit");
        System.out.println();
        System.out.println("Enter choice:");
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
        commandList.append("'inventory' to open your inventory\n");
        commandList.append("'take' to pick up item\n");
        commandList.append("'drop' to drop item\n");
        return commandList.toString();
    }

    private void processChoice(String choice) {
        switch (choice) {
            case "look":
                adventure.lookAround();
                break;
            case "help":
                adventure.helpUser(adventure.player.getCurrentRoom());
                break;
            case "n":
            case "go north":
                adventure.move("north");
                break;
            case "s":
            case "go south":
                adventure.move("south");
                break;
            case "w":
            case "go west":
                adventure.move("west");
                break;
            case "e":
            case "go east":
                adventure.move("east");
                break;
            case "take":
                System.out.println("Which item would you like to take?");
                String takeItemName = input.nextLine();
                adventure.takeItem(takeItemName);
                break;
            case "drop":
                System.out.println("Which item would you like to drop?");
                String dropItemName = input.nextLine();
                adventure.dropItem(dropItemName);
                break;
            case "inv":
            case "inventory":
                adventure.showInventory();
                break;
            case "exit":
                System.out.println("Exiting...");
                running = false;
                break;
            default:
                System.out.println("Invalid command. Try again.");
        }
    }
}