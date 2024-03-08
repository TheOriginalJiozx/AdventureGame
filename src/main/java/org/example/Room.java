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

    public String helpUser(String commands) {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("You are in room: ").append(name).append("\n");
        helpMessage.append("Description: ").append(description).append("\n");
        helpMessage.append("Available commands:\n");
        helpMessage.append(commands);
        return helpMessage.toString();
    }

    public void dropItems(ArrayList<Item> droppedItems) {
        this.droppedItems.addAll(droppedItems);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> allItems = new ArrayList<>(items);
        allItems.addAll(droppedItems);
        return allItems;
    }

    public ArrayList<Item> getDroppedItems() {
        return droppedItems;
    }

    public void addItems(Item item) {
        items.add(item);
    }
}