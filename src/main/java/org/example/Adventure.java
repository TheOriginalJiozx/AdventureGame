package org.example;

import java.util.ArrayList;

public class Adventure {
    private Player player;
    private Map map;

    public Adventure() {
        this.map = new Map();
        player = new Player(map.getFirstRoom());
    }

    public Map getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    public void helpUser() {
        player.helpUser();
    }

    public ArrayList<Item> lookAround() {
        return player.getCurrentRoom().getItems();
    }

    public Item takeItemFromRoom(String itemName) {
        return player.getCurrentRoom().takeItem(itemName);
    }

    public Item dropItemFromInventory(String itemName) {
        return player.dropItem(itemName);
    }

    public Item takeItemFromRoomByShortName(String shortName) {
        Room currentRoom = player.getCurrentRoom();
        for (Item item : currentRoom.getItems()) {
            if (item.getShortName().equalsIgnoreCase(shortName)) {
                currentRoom.dropItem(item.getName());
                return item;
            }
        }
        return null;
    }

    public Item dropItemFromInventoryByShortName(String shortName) {
        for (Item item : player.getInventory()) {
            if (item.getShortName().equalsIgnoreCase(shortName)) {
                player.dropItem(item.getName());
                return item;
            }
        }
        return null;
    }

    public Room go(Direction direction) {
        StringBuilder helpMessage = new StringBuilder();
        return player.go(direction);
    }

    public ArrayList<Item> getPlayerInventory() {
        return player.getInventory();
    }

    public void addToInventory(Item item) {
        player.addToInventory(item);
    }
}