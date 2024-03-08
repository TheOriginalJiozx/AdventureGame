package org.example;
public class AdventureGame {
    public static void main(String[] args) {
        Adventure adventure = new Adventure();
        UserInterface ui = new UserInterface(adventure);
        ui.startProgram();

    }
}