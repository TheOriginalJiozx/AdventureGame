package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private Room xyzzyRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom; //hvilket rum er du i nu
        this.inventory = new ArrayList<>(); //opretter en inventory
        this.xyzzyRoom = currentRoom;
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

    public Room getCurrentRoom() { //registrerer hvor du er nu
        return currentRoom;
    }

    public void saveXyzzyPosition() {
        xyzzyRoom = currentRoom;
    }

    public Room teleportToXyzzyPosition() {
        Room previousRoom = currentRoom;
        currentRoom = xyzzyRoom;
        xyzzyRoom = previousRoom;
        return previousRoom;
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