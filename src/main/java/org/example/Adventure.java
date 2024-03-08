package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adventure {
    private Player player;
    private Map map;

    public Adventure() {
        this.map = new Map();
        player = new Player(map.getFirstRoom());
    }

    public void helpUser() {
        player.helpUser();
    }

    public ArrayList<Item> lookAround() {
        ArrayList<Item> roomItems = player.getCurrentRoom().getItems();
        ArrayList<Item> visibleItems = new ArrayList<>();

        for (Item item : roomItems) {
            if (!item.isTaken()) {
                visibleItems.add(item);
            }
        }

        return visibleItems;
    }

    public ArrayList<Item> takeItemsFromRoom() {
        ArrayList<Item> itemsToTake = new ArrayList<>();
        ArrayList<Item> roomItems = player.getCurrentRoom().getItems();
        ArrayList<Item> droppedItems = player.getCurrentRoom().getDroppedItems();

        for (Item item : roomItems) {
            if (!item.isTaken()) {
                itemsToTake.add(item);
                item.setTaken(true);
            }
        }

        for (Item item : droppedItems) {
            if (!item.isTaken()) {
                itemsToTake.add(item);
                item.setTaken(true);
            }
        }

        return itemsToTake;
    }

    public Room go(Direction direction) {
        return player.go(direction);
    }

    public ArrayList<Item> getPlayerInventory() {
        return player.getInventory();
    }

    public void addToInventory(Item item) {
        player.addToInventory(item);
    }

    public void dropAllTakenItems() {
        ArrayList<Item> takenItems = player.getInventory();
        player.clearInventory();
        Room currentRoom = player.getCurrentRoom();
        currentRoom.dropItems(takenItems);
        System.out.println("You have dropped all taken items in " + currentRoom.getName() + ".");
    }
}