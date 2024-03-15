package org.example;

import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private Room northRoom;
    private Room southRoom;
    private Room eastRoom;
    private Room westRoom;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> droppedItems = new ArrayList<>();
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Food> droppedFoods = new ArrayList<>();
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private ArrayList<Weapon> droppedWeapons = new ArrayList<>();
    private boolean visited;
    private boolean westRoomLocked = false;
    private boolean lightsOff = false;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public boolean hasVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
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

    public ArrayList<Item> getItems() {
        ArrayList<Item> allItems = new ArrayList<>(items);
        allItems.addAll(droppedItems);
        return allItems;
    }
    public ArrayList<Food> getFoods() {
        ArrayList<Food> allFoods = new ArrayList<>(foods);
        allFoods.addAll(droppedFoods);
        return allFoods;
    }
    public ArrayList<Weapons> getWeapons() {
        ArrayList<Weapon> allWeapons = new ArrayList<>(weapons);
        allWeapons.addAll(droppedWeapons);
        return allWeapons;
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

    public Food takeFood(String foodName) {
        for (Food food : foods) {
            if (food.getName().equalsIgnoreCase(foodName) || food.getShortName().equalsIgnoreCase(foodName)) {
                foods.remove(food);
                return food;
            }
        }
        return null;
    }
    public Weapons takeWeapons(String weaponName) {
        for (Weapon weapon : weapons) {
            if (food.getName().equalsIgnoreCase(weaponName) || food.getShortName().equalsIgnoreCase(weaponName)) {
                foods.remove(food);
                return food;
            }
        }
        return null;
    }

    public void addItems(Item item) {
        items.add(item);
    }

    public void addFoods(Food food) {
        foods.add(food);
    }

    public void addWeapons(Weapon weapon) {
        weapons.add(weapon);
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
    public Weapon dropWeapon(String weaponName) {
        for (Weapon weapon : weapons) {
            if (weapon.getName().equalsIgnoreCase(weaponName)) {
                weapons.remove(weapon);
                return weapon;
            }
        }
        return null;
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
}