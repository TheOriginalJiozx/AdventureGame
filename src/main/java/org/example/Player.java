package org.example;

import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Item> inventoryItems;
    private int health;
    private Room xyzzyRoom;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.previousRoom = null;
        this.inventoryItems = new ArrayList<>();
        this.health = 100;
        this.xyzzyRoom = currentRoom;
    }

    public void decreaseHealth(int amount) {
        health = health-amount;
    }

    public void increaseHealth(int amount) {
        health = health+amount;
    }

    public void equipWeapon(String weaponNameOrShortName, UserInterface userInterface) {
        Item weapon = getItemFromInventory(weaponNameOrShortName);
        if (weapon == null) {
            weapon = getItemFromInventoryByShortName(weaponNameOrShortName);
        }
        if (weapon instanceof Weapon) {
            Weapon selectedWeapon = (Weapon) weapon;
            if (selectedWeapon.isEquipped()) {
                userInterface.weaponAlreadyEquippedMessage();
            } else {
                selectedWeapon.equip();
                userInterface.weaponEquippedMessage(selectedWeapon.getName());
                for (Item item : inventoryItems) {
                    if (item instanceof Weapon && !item.equals(selectedWeapon)) {
                        ((Weapon) item).unequip();
                    }
                }
            }
        } else {
            userInterface.weaponNotInInventoryMessage();
        }
    }

    public int getHealth(){
        return health;
    }

    public Room go(Direction direction, UserInterface userInterface) {
        Room nextRoom = null;

        if (currentRoom.getName().equals("Mine Tunnels") && currentRoom.areLightsOff()) {
            switch (direction) {
                case SOUTH:
                    if (currentRoom.getSouthRoom() != null && currentRoom.getSouthRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getSouthRoom();
                    }
                    break;
                case NORTH:
                    if (currentRoom.getNorthRoom() != null && currentRoom.getNorthRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getNorthRoom();
                    }
                    break;
                case WEST:
                    if (currentRoom.getWestRoom() != null && currentRoom.getWestRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getWestRoom();
                    }
                    break;
                case EAST:
                    if (currentRoom.getEastRoom() != null && currentRoom.getEastRoom().equals(previousRoom)) {
                        nextRoom = currentRoom.getEastRoom();
                    }
                    break;
            }
            if (nextRoom == null) {
                userInterface.displayDarkRoomMessage();
                return currentRoom;
            }
        } else {
            switch (direction) {
                case NORTH:
                    nextRoom = currentRoom.getNorthRoom();
                    break;
                case SOUTH:
                    nextRoom = currentRoom.getSouthRoom();
                    break;
                case EAST:
                    nextRoom = currentRoom.getEastRoom();
                    break;
                case WEST:
                    nextRoom = currentRoom.getWestRoom();
                    break;
            }
        }

        if (nextRoom != null) {
            if (!nextRoom.hasVisited()) {
                userInterface.displayVisitedRoomMessage(nextRoom.getDescription(), nextRoom.getName(), nextRoom.getShortName());
                nextRoom.setVisited(true);
            } else {
                userInterface.displayReturnRoomMessage(nextRoom.getName());
            }
            previousRoom = currentRoom;
            currentRoom = nextRoom;
        } else {
            userInterface.displayHitWallMessage();
        }

        return currentRoom;
    }

    public void removeFromInventory(Food food) {
        inventoryItems.remove(food);
    }

    public void removeFromInventory(Liquid liquid) {
        inventoryItems.remove(liquid);
    }

    public int getInventoryWeight() {
        int totalWeight = 0;
        for (Item item : inventoryItems) {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void saveXyzzyPosition() {
        xyzzyRoom = currentRoom;
    }

    public void addToInventory(Item item) {
        inventoryItems.add(item);
    }

    public ArrayList<Item> getInventoryItems() {
        return inventoryItems;
    }

    public Item getItemFromInventory(String name) {
        for (Item item : inventoryItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public Item getItemFromInventoryByShortName(String shortName) {
        for (Item item : inventoryItems) {
            if (item.getShortName().equalsIgnoreCase(shortName)) {
                return item;
            }
        }
        return null;
    }

    public Item dropItem(String name) {
        for (Item item : inventoryItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                inventoryItems.remove(item);
                return item;
            }
        }
        return null;
    }

    public Room teleportToXyzzyPosition() {
        Room previousRoom = currentRoom;
        currentRoom = xyzzyRoom;
        xyzzyRoom = previousRoom;
        UserInterface ui = new UserInterface();
        ui.teleportationMessage(currentRoom.getName());
        return currentRoom;
    }

    public void useWeapon() {
        Player player = this;
        Weapon equippedWeapon = null;

        for (Item item : player.getInventoryItems()) {
            if (item instanceof Weapon) {
                Weapon weapon = (Weapon) item;
                if (weapon.isEquipped()) {
                    equippedWeapon = weapon;
                    break;
                }
            }
        }

        if (equippedWeapon != null) {
            if (equippedWeapon instanceof MeleeWeapon) {
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                if (rangedWeapon.getAmmonition() > 0) {
                    Room currentRoom = player.getCurrentRoom();
                    ArrayList<Enemy> enemies = currentRoom.getEnemies();
                    if (!enemies.isEmpty()) {
                        Enemy enemy = enemies.get(0);
                        int damageDealt = rangedWeapon.getDamage();
                        enemy.takeDamage(damageDealt);
                        if (enemy.isDefeated()) {
                            currentRoom.removeEnemy(enemy);
                        }
                        rangedWeapon.decreaseAmmonition();
                        enemyAttack(enemy, player);
                    } else {
                        UserInterface ui = new UserInterface();
                        ui.weaponNoEnemies();
                    }
                } else {
                    UserInterface ui = new UserInterface();
                    ui.weaponNoAmmunition(equippedWeapon.getName());
                }
            }
        } else {
            UserInterface ui = new UserInterface();
            ui.weaponNotEquipped();
        }
    }

    private void enemyAttack(Enemy enemy, Player player) {
        int playerHealthBeforeAttack = player.getHealth();
        int damageDealt = enemy.getDamage();
        player.decreaseHealth(damageDealt);
        int playerHealthAfterAttack = player.getHealth();

        UserInterface ui = new UserInterface();
        ui.enemyAttacked(enemy.getName(), damageDealt, playerHealthBeforeAttack, playerHealthAfterAttack);

        if (playerHealthAfterAttack <= 0) {
            ui.checkGameOver();
        } else {
            if (enemy.getHealth() <= 0) {
                ui.defeatedEnemy(enemy.getName());
                ui.checkGameOver();
            }
        }
    }

    public void craftItem(Item item) {
        inventoryItems.add(item);
    }
}