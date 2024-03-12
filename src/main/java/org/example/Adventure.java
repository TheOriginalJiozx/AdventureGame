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

    public Item takeItemFromRoom(String itemName) { //samler ting op fra rummet
        return player.getCurrentRoom().takeItem(itemName);
    }

    public Item dropItemFromInventory(String itemName) { //smider noget fra inventory
        return player.dropItem(itemName);
    }

    public Item takeItemFromRoomByShortName(String shortName) { //samler op fra rummet med "short name"
        Room currentRoom = player.getCurrentRoom(); //hvor er du nu
        for (Item item : currentRoom.getItems()) { //hvad er i rummet
            if (item.getShortName().equalsIgnoreCase(shortName)) { //fanger short name fra Map
                currentRoom.dropItem(item.getName()); //forbinder til name i Map
                return item;
            }
        }
        return null;
    }

    public Item dropItemFromInventoryByShortName(String shortName) { //smider fra inventory med "short name"
        for (Item item : player.getInventory()) { //hvad er i din inventory
            if (item.getShortName().equalsIgnoreCase(shortName)) { //fanger short name fra Map
                player.dropItem(item.getName()); //forbinder til name i Map
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