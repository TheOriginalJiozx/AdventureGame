package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room {
    private String name;
    private String description;
    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> droppedItems = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private boolean visited;
    private boolean westRoomLocked = false;
    private boolean eastRoomLocked = false;
    private boolean northRoomLocked = false;
    private boolean southRoomLocked = false;
    private boolean lightsOff = false;
    private Set<Direction> triedDirections = new HashSet<>();
    private int ammonition;
    private String shortName;
    public Music music;
    private boolean shouldReplayMusic;
    public boolean hasMusic;
    private boolean musicPlaying;
    private List<Room> adjacentRooms;
    private static List<Room> allRooms = new ArrayList<>();

    public Room(String name, String description, String songFilePath) {
        this.name = name;
        this.description = description;
        this.ammonition = Integer.MAX_VALUE;
        this.shortName = generateShortName(name);
        if (songFilePath != null) {
            this.music = new Music(songFilePath);
            this.music.playMusic();
        }
        this.shouldReplayMusic = false;
        hasMusic = false;
        musicPlaying = false;
        this.name = name;
        this.adjacentRooms = new ArrayList<>();
        this.visited = false;
    }

    public boolean hasMusic() {
        return music != null;
    }

    public void setMusic(Music music) {
        this.music = music;
        // Set hasMusic flag to true if music is provided
        hasMusic = true;
    }

    public void playMusic() {
        // Check if the room has music and if it's not already playing
        if (hasMusic && music != null && !music.isPlaying()) {
            // Stop previous music if it's playing
            stopPreviousMusic();
            // Play the new music
            music.play();
            musicPlaying = true; // Update the musicPlaying flag
        }
    }

    private void stopPreviousMusic() {
        // Stop previous music only if it's playing
        if (music != null && music.isPlaying()) {
            music.stop();
            musicPlaying = false; // Update the musicPlaying flag
        }
    }

    public void stopMusic() {
        // Stop music only if the room has music and it's playing
        if (hasMusic && music != null && music.isPlaying()) {
            music.stop();
        }
    }

    public Room(String name, String description) {
        this(name, description, null);
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

    public Room getNeighbor(Direction direction) {
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

    public boolean hasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean allDirectionsTried() {
        return triedDirections.size() == Direction.values().length;
    }

    public void tryDirection(Direction direction) {
        triedDirections.add(direction);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> allItems = new ArrayList<>(items);
        allItems.addAll(droppedItems);
        return allItems;
    }

    public Item takeItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void addItems(Item item) {
        items.add(item);
    }

    public Item dropItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public void setAmmonition(int ammonition) {
        this.ammonition = ammonition;
    }

    public boolean isWestRoomLocked() {
        return westRoomLocked;
    }

    public void unlockWestRoom() {
        westRoomLocked = false;
    }

    public void lockWestRoom() {
        westRoomLocked = true;
    }

    public boolean isEastRoomLocked() {
        return eastRoomLocked;
    }

    public void unlockEastRoom() {
        eastRoomLocked = false;
    }

    public void lockEastRoom() {
        eastRoomLocked = true;
    }

    public boolean isNorthRoomLocked() {
        return northRoomLocked;
    }

    public void unlockNorthRoom() {
        northRoomLocked = false;
    }

    public void lockNorthRoom() {
        northRoomLocked = true;
    }

    public boolean isSouthRoomLocked() {
        return southRoomLocked;
    }

    public void unlockSouthRoom() {
        southRoomLocked = false;
    }

    public void lockSouthRoom() {
        southRoomLocked = true;
    }

    public boolean areLightsOff() {
        return lightsOff;
    }

    public void turnOnLights() {
        lightsOff = false;
    }

    public void turnOffLights() {
        lightsOff = true;
    }

    public boolean areLightsOn() {
        return !lightsOff;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public String getShortName() {
        return shortName;
    }

    private String generateShortName(String longName) {
        StringBuilder shortNameBuilder = new StringBuilder();
        String[] words = longName.split(" ");
        if (words.length > 0) {
            for (int i = words.length - 1; i >= 0; i--) {
                String word = words[i];
                if (!word.equalsIgnoreCase("of") && !word.equalsIgnoreCase("the") &&
                        !word.equalsIgnoreCase("and") && !word.equalsIgnoreCase("or")) {
                    shortNameBuilder.append(Character.toUpperCase(word.charAt(0)));
                    if (word.length() > 1) {
                        shortNameBuilder.append(word.substring(1).toLowerCase());
                    }
                    break;
                }
            }
        } else {
            shortNameBuilder.append(longName.toUpperCase());
        }
        return shortNameBuilder.toString();
    }
}