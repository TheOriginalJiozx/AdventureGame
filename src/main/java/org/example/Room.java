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
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<Thief> thieves = new ArrayList<>();
    private ArrayList<PassiveEnemy> passiveEnemies = new ArrayList<>();
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
    public boolean hasMusic;
    private boolean musicPlaying;
    private List<Room> adjacentRooms;
    private static List<Room> allRooms = new ArrayList<>();
    private Thief thief;
    private Player player;

    public Room(String name, String description, String songFilePath) {
        this.name = name;
        this.description = description;
        this.ammonition = Integer.MAX_VALUE;
        this.shortName = generateShortName(name);
        if (songFilePath != null) {
            this.music = new Music(songFilePath);
            this.music.playMusic();
        }
        hasMusic = false;
        this.name = name;
        this.adjacentRooms = new ArrayList<>();
        this.visited = false;
        this.thief = null;
        this.player = player;
    }

    public void setMusic(Music music) {
        this.music = music;
        hasMusic = true;
    }

    public void playMusic() {
        if (hasMusic && !music.isPlaying()) {
            stopPreviousMusic();
            music.play();
            musicPlaying = true;
        }
    }

    private void stopPreviousMusic() {
        if (music != null && music.isPlaying()) {
            music.stop();
            musicPlaying = false;
        }
    }

    public void stopMusic() {
        if (hasMusic && music.isPlaying()) {
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

    public ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public void removeNPC(NPC npc) {
        npcs.remove(npc);
    }

    public ArrayList<Thief> getThieves() {
        return thieves;
    }

    public void addThief(Thief thief) {
        thieves.add(thief);
    }

    public boolean hasThief() {
        return !thieves.isEmpty();
    }

    public void removeThief(Thief thief) {
        thieves.remove(thief);
    }

    public void passiveEnemyLoot(PassiveEnemy passiveEnemy, Room currentRoom) {
    }

    public void removePassiveEnemy(PassiveEnemy passiveEnemy) {
        npcs.remove(passiveEnemy);
    }

    public ArrayList<PassiveEnemy> getPassiveEnemies() {
        return passiveEnemies;
    }

    public void enemyLoot(Enemy enemy, Room currentRoom) {
        String enemyName = enemy.getName();
        switch (enemyName) {
            case "Goblin King":
                currentRoom.addItems(new MeleeWeapon("King David's Dagger", 50, 3500, currentRoom));
                break;
            case "Putin":
                currentRoom.addItems(new RangedWeapon("Putin's Bazooka", 50, 10000, 10000, currentRoom));
                break;
            case "H.C. Andersen":
                currentRoom.addItems(new MeleeWeapon("Danmarks Våben", 70, 4500, currentRoom));
                break;
            case "Satan":
                currentRoom.addItems(new RangedWeapon("The Devils Flamethrower", 50, 20, 7000, currentRoom));
                break;
            case "Unicornious":
                currentRoom.addItems(new MeleeWeapon("Unicon Sword", 20, 4000, currentRoom));
                break;
            case "Tarzan":
                currentRoom.addItems(new MeleeWeapon("Tarzan's Spear", 70, 3500, currentRoom));
                break;
            case "Harley Quinn":
                currentRoom.addItems(new MeleeWeapon("King Kong's Fist", 60, 5500, currentRoom));
                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Bat", 70, 2500, currentRoom));
                currentRoom.addItems(new RangedWeapon("Harley Quinn's Joke Gun", 200, 15, 4000, currentRoom));
                break;
            case "Mars Alien":
                currentRoom.addItems(new RangedWeapon("Magnetic Railgun", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 8000, currentRoom));
                currentRoom.addItems(new RangedWeapon("Atomic Bomb", 35, 5000, 15000, currentRoom));
                break;
            case "Batman":
                currentRoom.addItems(new RangedWeapon("Batman's Batarang", 20, 5, 500, currentRoom));
                currentRoom.addItems(new MeleeWeapon("Batman's Batknife", 30, 2500, currentRoom));
                break;
            case "Ricardo Diaz":
                currentRoom.addItems(new RangedWeapon("Vice City Shotgun", 100, 10, 6000, currentRoom));
                break;
            case "The Joker":
                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Hammer", 100, 4000, currentRoom));
                break;
            case "Traitor Lord":
                currentRoom.addItems(new MeleeWeapon("King David's Sword", 300, 7000, currentRoom));
                break;
            case "The Pharaoh":
                currentRoom.addItems(new MeleeWeapon("Pharaoh's Scepter", 80, 4000, currentRoom));
                currentRoom.addItems(new RangedWeapon("Sandstorm Blaster", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 7000, currentRoom));
                break;
            case "Goliath":
                currentRoom.addItems(new MeleeWeapon("Staff of Moses", 100, 5000, currentRoom));
                currentRoom.addItems(new MeleeWeapon("Sword of Goliath", 150, 6000, currentRoom));
                break;
            case "Cyber Athlete":
                currentRoom.addItems(new MeleeWeapon("Deceiver Killer Sword", 500, 7000, currentRoom));
                currentRoom.addItems(new RangedWeapon("X-Ray Rifle", 40, 50, 7000, currentRoom));
                break;
            case "Zombie":
                currentRoom.addItems(new MeleeWeapon("Cold Steel Rapier", 40, 3500, currentRoom));
                break;
            case "Samael":
                currentRoom.addItems(new MeleeWeapon("Sword of Angels", 300, 6000, currentRoom));
                currentRoom.addItems(new MeleeWeapon("Spear of Destiny", 250, 5000, currentRoom));
                break;
            case "Deceiver":
                currentRoom.addItems(new MeleeWeapon("Zombie Killer Sword", 500, 5000, currentRoom));
                break;
            default:
                break;
        }
    }

    public void thiefLoot(Thief thief, Room currentRoom, Player player) {
        ArrayList<Item> stolenItems = thief.getInventoryItems();
        if (!stolenItems.isEmpty()) {
            for (Item item : stolenItems) {
                player.addToInventory(item); // Add stolen item to the room
            }
            System.out.println(thief.getName() + " dropped the loot:");
            for (Item item : stolenItems) {
                System.out.println("- " + item.getName());
            }
        } else {
            System.out.println(thief.getName() + " had no loot to drop.");
        }
    }

    public Player getPlayer() {
        return player; // Assuming 'player' is a field in your Room class that holds the player object
    }

    public void npcLoot(NPC npc, Room currentRoom) {
        String npcName = npc.getName();
        switch (npcName) {
            case "Andrew Johnson":
                currentRoom.addItems(new Item("Zeus Destroyer Component 1", 50));
                break;
            case "Hannibal Hamlin":
                currentRoom.addItems(new Item("Zeus Destroyer Component 2", 50));
                break;
            case "Tublat":
                currentRoom.addItems(new Item("Zeus Destroyer Component 3", 50));
                break;
            case "Unicorn Baby":
                currentRoom.addItems(new Item("Zeus Destroyer Component 4", 50));
                break;
            case "Ken Rosenberg":
                currentRoom.addItems(new Item("Zeus Destroyer Component 5", 50));
                break;
            default:
                break;
        }
    }

    public String getShortName() {
        return shortName;
    }

    public void enterRoom(Player player, Weapon weapon, UserInterface userInterface) {
        for (Enemy enemy : enemies) {
            enemy.playerEntered(player, weapon);
        } for (Thief thief : thieves) {
            player.handlePlayerThiefInteraction(thief, userInterface);
        }
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