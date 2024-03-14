package org.example;

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