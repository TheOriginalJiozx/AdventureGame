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
        System.out.println("You wake up completely lost and totally confused. You have no memory of who you are, who you were or where you are. You feel frightened, restless and excited for the amazing adventure that lies ahead. You have a note in your hand:\n" +
                "YOU MUST FIND THE DOOR WITH THE ROOM BEHIND FILLED WITH EVERYTHING YOU HAVE EVER DESIRED\n" +
                "\n" +
                "You begin to figure out in which direction you will travel.");


        while (running) {

            displayMenu();
            String choice = input.nextLine().toLowerCase();
            processChoice((choice));
        }
        input.close();
    }


    private void displayMenu() {
        System.out.println("Menu");
        System.out.println("Help");
        System.out.println("Show inventory(inv)");
        System.out.println("Look around(look)");
        System.out.println("Take");
        System.out.println("Drop(d)");
        System.out.println("Go north(n)");
        System.out.println("Go south(s)");
        System.out.println("Go west(w)");
        System.out.println("Go east(e)");
        System.out.println("Exit");
        System.out.println("Enter choice");
    }

    private void processChoice(String choice) {
        switch (choice) {
            case "look":
                adventure.lookAround();
                break;
            case "help":
                System.out.println("help...");
                break;
            case "n":
            case "north":
                adventure.move("north");
                break;
            case "s":
            case "south":
                adventure.move("south");
                break;
            case "w":
            case "west":
                adventure.move("west");
                break;
            case "e":
            case "east":
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
                System.out.println("Invalid command");
        }
    }
}