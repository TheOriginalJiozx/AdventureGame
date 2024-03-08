package org.example;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private List<Item> items;

    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;

    public Room(String name, String description){
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
    }
    public Room getNorthRoom(){
        return northRoom;
    }
    public void setNorthRoom(Room northRoom){
        this.northRoom = northRoom;
    }
    public Room getSouthRoom(){
        return southRoom;
    }
    public void setSouthRoom(Room southRoom){
        this.southRoom = southRoom;
    }
    public Room getEastRoom(){
        return eastRoom;
    }
    public void setEastRoom(Room eastRoom){
        this.eastRoom = eastRoom;
    }
    public Room getWestRoom(){
        return westRoom;
    }
    public void setWestRoom(Room westRoom){
        this.westRoom = westRoom;
    }
    public void addItem(Item item){
        items.add(item);
    }
    public void removeItem(Item item){
        items.remove(item);
    }
    public List<Item> getItems(){
        return items;
    }
    public Item findItem(String itemName){
        for(Item item : items){
            if (item.getName().equalsIgnoreCase(itemName)){
                return item;
            }
        }
        return null;
    }



    public void lookAround(){
        System.out.println("You are in " + name);
        System.out.println(description);
        if (!items.isEmpty()){
            System.out.println("You see the following items: ");
            for (Item item : items){
                System.out.println("- " + item.getName());
            }
        }


    }
}