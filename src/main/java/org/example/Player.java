package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private boolean westRoomLocked;
    private boolean westRoomUnlocked;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom; //hvilket rum er du i nu
        this.inventory = new ArrayList<>(); //opretter en inventory
        this.westRoomLocked = true; //den starter med at være låst
        this.westRoomUnlocked = false; //når du skriver 'unlock' så låser du op
    }

    public Room go(Direction direction) {
        Room nextRoom = null;
        switch (direction) {
            case NORTH:
                nextRoom = currentRoom.getNorthRoom(); //den registrerer inde fra Room og Map hvor northRoom er
                break;
            case SOUTH:
                nextRoom = currentRoom.getSouthRoom(); //den registrerer inde fra Room og Map hvor southRoom er
                break;
            case EAST:
                nextRoom = currentRoom.getEastRoom(); //den registrerer inde fra Room og Map hvor eastRoom er
                break;
            case WEST:
                if (currentRoom.getName().equals("Room 9") && currentRoom.isWestLocked()) { //rummet til venstre er låst
                    System.out.println("The room is locked. Enter 'unlock' to unlock the room.");
                    return currentRoom;
                }
                nextRoom = currentRoom.getWestRoom(); //den registrerer inde fra Room og Map hvor westRoom er
                break;
        }

        if (nextRoom != null) {
            if (!nextRoom.hasVisited()) { //hvis du går tilbage til et rum, som er blevet besøgt, får du det her vist
                System.out.println("You have gone to " + nextRoom.getName() + ". " + "This is a " + nextRoom.getDescription());
                nextRoom.setVisited(true); //fortæller, at du har besøgt tidligere
            } else {
                System.out.println("You have gone back to " + nextRoom.getName() + ". " + "What now? ");
            }
            currentRoom = nextRoom;
        } else {
            System.out.println("You have hit a wall! Try again."); //hvis du går i en forkert, så rammer du en væg
        }

        return currentRoom; //viser hvor du er nu
    }

    public void unlockWestRoom() { //metode som låser rummet vest for rum 9
        if (currentRoom.getName().equals("Room 9")) {
            currentRoom.unlockWestRoom();
        }
    }

    public Room getCurrentRoom() { //registrerer hvor du er nu
        return currentRoom;
    }

    public void addToInventory(Item item) { //når du samler noget op, så bliver det sendt ind i din inventory
        inventory.add(item);
    }

    public ArrayList<Item> getInventory() { //viser din inventory når du taster "inventory"
        return inventory;
    }

    public Item dropItem(String itemName) { //metoden, som smider item(s) væk
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                return item;
            }
        }
        return null;
    }
}