package org.example;

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
        return player.getCurrentRoom().getItems();
    }

    public Room go(Direction direction) {
        return player.go(direction);
    }

    public void helpUser(Room room) {
        room.helpUser();
    }

    // Getter method to access the Player object
    public Player getPlayer() {
        return player;
    }
}