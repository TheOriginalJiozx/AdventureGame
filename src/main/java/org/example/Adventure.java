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

    public ArrayList<ItemOrFood> lookAround() {
        ArrayList<ItemOrFood> itemsAndFoods = new ArrayList<>();
        itemsAndFoods.addAll(player.getCurrentRoom().getItems());
        itemsAndFoods.addAll(player.getCurrentRoom().getFoods());
        return itemsAndFoods;
    }

    public Food takeFoodFromRoom(String foodName) {
        return player.getCurrentRoom().takeFood(foodName.trim().toLowerCase());
    }

    public Food dropFoodFromInventory(String foodName) {
        return player.dropFood(foodName.trim().toLowerCase());
    }

    public Food takeFoodFromRoomByShortName(String shortName) {
        return player.getCurrentRoom().takeFood(shortName.trim().toLowerCase());
    }

    public Food dropFoodFromInventoryByShortName(String shortName) {
        return player.dropFood(shortName.trim().toLowerCase());
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
        for (Item item : player.getInventoryItems()) {
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
        return player.getInventoryItems();
    }

    public void addToInventory(Item item) { //tilføjer til inventory
        player.addToInventory(item);
    }

    public String unlockWestRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 9")) {
            if (currentRoom.isWestRoomLocked()) {
                currentRoom.unlockWestRoom();
                return "West room unlocked!";
            } else {
                return "West room is already unlocked.";
            }
        } else {
            return "You can only unlock the west room from Room 9.";
        }
    }

    public boolean tryUnlockWestRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 9")) {
            return currentRoom.isWestRoomLocked();
        }
        return false;
    }
}