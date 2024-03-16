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

    public void increaseHealth(int amount){
        health = health+amount;
    }

    public void equipWeapon(String weaponNameOrShortName) {
        Item weapon = getItemFromInventory(weaponNameOrShortName);
        if (weapon == null) {
            weapon = getItemFromInventoryByShortName(weaponNameOrShortName);
        }
        if (weapon instanceof Weapon) {
            Weapon selectedWeapon = (Weapon) weapon;
            if (selectedWeapon.isEquipped()) {
                System.out.println("The weapon is already equipped.");
            } else {
                selectedWeapon.equip();
                System.out.println("You have equipped " + selectedWeapon.getName() + ".");
                for (Item item : inventoryItems) {
                    if (item instanceof Weapon && !item.equals(selectedWeapon)) {
                        ((Weapon) item).unequip();
                    }
                }
            }
        } else {
            System.out.println("You don't have such weapon in your inventory.");
        }
    }

    public int getHealth(){
        return health;
    }

    public Room go(Direction direction) {
        Room nextRoom = null;

        if (currentRoom.getName().equals("Room 3") && currentRoom.areLightsOff()) {
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
                System.out.println("It's too dark to see anything. You can't move to other rooms until you turn on the lights.");
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
                System.out.println(nextRoom.getDescription() + "You have gone to " + nextRoom.getName() + ".");
                nextRoom.setVisited(true);
            } else {
                System.out.println("You have gone back to " + nextRoom.getName() + ". " + "What now? ");
            }
            previousRoom = currentRoom;
            currentRoom = nextRoom;
        } else {
            System.out.println("You have hit a wall! Try again.");
        }

        return currentRoom;
    }

    public void removeFromInventory(Food food) {
        inventoryItems.remove(food);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void saveXyzzyPosition() {
        xyzzyRoom = currentRoom;
    }

    public Room teleportToXyzzyPosition() {
        Room previousRoom = currentRoom;
        currentRoom = xyzzyRoom;
        xyzzyRoom = previousRoom;
        System.out.println("You have teleported back to: " + currentRoom.getName());
        return currentRoom;
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
                        System.out.println("You attacked the enemy with " + equippedWeapon.getName() + " and dealt " + damageDealt + " damage!");
                        if (enemy.isDefeated()) {
                            currentRoom.removeEnemy(enemy);
                        }
                        rangedWeapon.decreaseAmmonition();
                        enemyAttack(enemy, player);
                    } else {
                        System.out.println("There are no enemies in this room to attack.");
                    }
                } else {
                    System.out.println("Your " + equippedWeapon.getName() + " has no more ammunition left.");
                }
            }
        } else {
            System.out.println("You don't have a weapon equipped.");
        }
    }

    public void enemyAttack(Enemy enemy, Player player) {
        int playerHealthBeforeAttack = player.getHealth();
        int damageDealt = enemy.getDamage();
        player.decreaseHealth(damageDealt);
        int playerHealthAfterAttack = player.getHealth();

        System.out.println("The enemy attacked you and dealt " + damageDealt + " damage.");
        System.out.println("Your health decreased from " + playerHealthBeforeAttack + " to " + playerHealthAfterAttack);

        if (playerHealthAfterAttack <= 0) {
            System.out.println("You have been defeated by the enemy!");
            gameOver();
        } else {
            if (enemy.getHealth() <= 0) {
                System.out.println("You have defeated the " + enemy.getName() + "!");
            }
        }
    }

    private void gameOver() {
        System.out.println("Game Over!");
        System.exit(0);
    }
}