package org.example;

public class Room {
    private Room currentRoom;
    private String name;
    private String description;
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void helpUser() {
        System.out.println("You are in room: " + getName());
        System.out.println("List of commands: ");
        System.out.println(new UserInterface().commands());
    }

    public void lookAround() {
        System.out.println("Room description: " + getDescription());
    }
}