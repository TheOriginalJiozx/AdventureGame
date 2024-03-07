package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Room {
    private String name;
    private String description;
    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;
    private ArrayList<Item> items = new ArrayList<>();

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

    public void helpUser() {
        System.out.println("You are in room: " + getName());
        System.out.println("List of commands: ");
        System.out.println(new UserInterface().commands());
    }

    public void lookAround() {
        System.out.println("Room description: " + getDescription());
    }

    public Room getRoom(Direction direction) {
        switch (direction) {
            case NORTH:
                return northRoom;
            case SOUTH:
                return southRoom;
            case EAST:
                return eastRoom;
            case WEST:
                return westRoom;
            default:
                return null;
        }
    }

    public void takeItems() {
    List list = new List();
        list.add("Golden Key");
    }

    public java.util.List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItems(Item item)
    {
        items.add(item);
    }
}