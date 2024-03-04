package org.example;
import java.util.Scanner;
public class UserInterface {
    private Scanner scanner = new Scanner(System.in);

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
}