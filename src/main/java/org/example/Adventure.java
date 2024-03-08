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

    public void helpUser() {
        player.helpUser();
    }

    public ArrayList<Item> lookAround() {
        return player.getCurrentRoom().getItems();
    }

    public ArrayList<Item> takeItemsFromRoom() {
        ArrayList<Item> itemsToTake = new ArrayList<>();
        itemsToTake.addAll(player.getCurrentRoom().getItems());
        itemsToTake.addAll(player.getCurrentRoom().getDroppedItems());
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