package org.example;

import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> droppedItems = new ArrayList<>();
    private boolean visited;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean hasVisited() { //registrerer om du har besøgt
        return visited;
    }

    public void setVisited(boolean visited) { //registrerer, at du har besøgt
        this.visited = visited;
    }

    public String getName() { //registrerer rummets navn
        return name;
    }

    public String getDescription() { //registrerer rummets beskrivelse
        return description;
    }

    public void setNorthRoom(Room northRoom) {
        this.northRoom = northRoom;
    }

    public void setSouthRoom(Room southRoom) {
        this.southRoom = southRoom;
    }

    public void setEastRoom(Room eastRoom) {
        this.eastRoom = eastRoom;
    }

    public void setWestRoom(Room westRoom) {
        this.westRoom = westRoom;
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public String helpUser(String commands) { //help metoden, som giver brugeren en hjælpelinje
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("You are in room: ").append(name).append("\n");
        helpMessage.append("Description: ").append(description).append("\n");
        helpMessage.append("Available commands:\n");
        helpMessage.append(commands);
        return helpMessage.toString();
    }

    public ArrayList<Item> getItems() { //viser dine items
        ArrayList<Item> allItems = new ArrayList<>(items);
        allItems.addAll(droppedItems);
        return allItems;
    }

    public Item takeItem(String itemName) { //metoden som gør det muligt at oprette en take funktion
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void addItems(Item item) { //metoden som gør det muligt at oprette en item i rummet
        items.add(item);
    }

    public Item dropItem(String itemName) { //metoden som gør det muligt at oprette en drop funktion
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }
}