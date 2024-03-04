package org.example;
import java.util.Scanner;
public class UserInterface {
    private Scanner scanner = new Scanner(System.in);

    public void startProgram() {
        char choice;
        do {
            displayMenu();
            choice = getUserChoice():
            handleChoice(choice);
        } while (choice != '5');

        scanner.close();
    }
    private void displayMenu() {
        System.out.println("Menu");
        System.out.println("1. Go north");
        System.out.println("2. Go south");
        System.out.println("3. Go east");
        System.out.println("4. Go west");
        System.out.println("5. Exit");
        System.out.println();
        System.out.println("Enter your choice: ");
    }
    private char getUserChoice() {
        return scanner.next().charAt(0);
    }

    private void handleChoice(char choice) {
        switch (choice) {
            case '1':
                goNorth();
                break;
            case '2':
                goSouth();
                break;
            case '3':
                goEast();
                break;
            case '4':
                goWest();
                break;
            case '5':
                System.out.println();
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private void goNorth() {
        AdventureGame.goNorth(room, scanner);
    }

    private void goSouth() {
        AdventureGame.goSouth(room, scanner);
    }

    private void goEast() {
        AdventureGame.goEast(room, scanner);
    }

    private void goWest() {
        AdventureGame.goWest(room, scanner);
    }
}