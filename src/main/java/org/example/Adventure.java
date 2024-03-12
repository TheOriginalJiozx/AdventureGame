package org.example;

import java.util.ArrayList;

public class Adventure {
    private Player player;
    private Map map;

    public Adventure() {
        this.map = new Map();
        player = new Player(map.getFirstRoom());
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Item> lookAround() { //viser hvad er i rummet
        return player.getCurrentRoom().getItems();
    }

    public Item takeItemFromRoom(String itemName) {
        return player.getCurrentRoom().takeItem(itemName.trim().toLowerCase());
    }

    public Item dropItemFromInventory(String itemName) {
        return player.dropItem(itemName.trim().toLowerCase());
    }

    public Item takeItemFromRoomByShortName(String shortName) {
        Room currentRoom = player.getCurrentRoom();
        for (Item item : currentRoom.getItems()) {
            if (item.getShortName().equalsIgnoreCase(shortName.trim().toLowerCase())) {
                currentRoom.dropItem(item.getName());
                return item;
            }
        }
        return null;
    }

    public Item dropItemFromInventoryByShortName(String shortName) {
        for (Item item : player.getInventory()) {
            if (item.getShortName().equalsIgnoreCase(shortName.trim().toLowerCase())) {
                player.dropItem(item.getName());
                return item;
            }
        }
        return null;
    }

    public Room go(Direction direction) { //finder veje man kan gå
        return player.go(direction);
    }

    public ArrayList<Item> getPlayerInventory() { //viser inventory
        return player.getInventory();
    }

    public void addToInventory(Item item) { //tilføjer til inventory
        player.addToInventory(item);
    }
}