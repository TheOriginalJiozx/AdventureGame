package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Item> inventoryItems;
    private int health;
    private Room xyzzyRoom;
    private Music music;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.previousRoom = null;
        this.inventoryItems = new ArrayList<>();
        this.health = 10;
        this.xyzzyRoom = currentRoom;
    }

    public void decreaseHealth(int amount) {
        health = health-amount;
    }

    public void increaseHealth(int amount) {
        health = health+amount;
    }

    public void equipWeapon(String weaponNameOrShortName, UserInterface userInterface) {
        if (!userInterface.isViewInventory()) {
            userInterface.equipWeaponViewInventoryPrompt();
            return;
        }

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

    public void takeItem(Adventure adventure, UserInterface userInterface) {
        if (!userInterface.isLookDisplayed()) {
            System.out.println(userInterface.takeItemLookPrompt());
            return;
        }
        Item item = userInterface.enterItemNamePrompt();

        if (item != null) {
            Player player = adventure.getPlayer();
            int currentWeight = player.getInventoryWeight();
            int maxCarry = item.getMaxCarry();
            int itemWeight = item.getWeight();

            if (currentWeight + itemWeight > maxCarry) {
                userInterface.maxWeightPrompt();
            } else if (currentWeight + itemWeight == maxCarry) {
                adventure.getPlayer().addToInventory(item);
                userInterface.takenItemWarningPrompt(item);
            } else {
                adventure.getPlayer().addToInventory(item);
                userInterface.takenItemPrompt(item);
            }
        }
    }

    public void dropItem(Adventure adventure, UserInterface userInterface) {
        if (!userInterface.isViewInventory()) {
            userInterface.dropItemViewInventoryPrompt();
            return;
        }
        String itemName = userInterface.promptDropItemName();
        Item item = adventure.dropItemFromInventory(itemName);
        if (item == null) {
            item = adventure.dropItemFromInventoryByShortName(itemName);
        }
        if (item != null) {
            adventure.getPlayer().getCurrentRoom().addItems(item);
            userInterface.droppedItemPrompt(item);
        } else {
            userInterface.droppedItemNotFound();
        }
    }

    public void eat(UserInterface userInterface, Adventure adventure) {
        if (!userInterface.isViewInventory()) {
            userInterface.eatViewInventoryPrompt();
            return;
        }

        String foodName = userInterface.promptFoodName();
        Item item = adventure.getPlayer().getItemFromInventory(foodName);
        if (item == null) {
            item = adventure.getPlayer().getItemFromInventoryByShortName(foodName);
        }
        if (item != null && item instanceof Food) {
            Food food = (Food) item;
            int healthChange = food.getHealthPoints();
            if (healthChange > 0) {
                userInterface.healthChange(healthChange, food);
            } else if (healthChange < 0) {
                String input = userInterface.confirmEatingUnhealthyFood();
                if (input.equals("yes")) {
                    userInterface.healthDecreaseChange(healthChange, food);
                } else if (input.equals("no")) {
                    String action = userInterface.keepOrDropFoodPrompt();
                    if (action.equals("keep")) {
                        userInterface.keepFoodPrompt(food);
                    } else if (action.equals("drop")) {
                        adventure.getPlayer().dropItem(food, adventure, userInterface);
                    } else {
                        userInterface.eatOrDrinkInvalidInputKeepOrDrop();
                    }
                } else {
                    userInterface.eatOrDrinkInvalidInputYesOrNo();
                }
            } else {
                userInterface.itemNotEdible();
            }
            adventure.getPlayer().removeFromInventory(food);
            if (adventure.getPlayer().getHealth() <= 0) {
                userInterface.foodGameOver();
            }
        } else {
            userInterface.foodNotFound();
        }
    }

    public void dropItem(Food food, Adventure adventure, UserInterface userInterface) {
        Player player = adventure.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        currentRoom.addItems(food);
        player.removeFromInventory(food);
        userInterface.droppedItemPrompt(food);
    }

    public void drink(UserInterface userInterface, Adventure adventure) {
        if (!userInterface.isViewInventory()) {
            userInterface.liquidViewInventoryPrompt();
            return;
        }

        String liquidName = userInterface.promptLiquidName();
        Item item = adventure.getPlayer().getItemFromInventory(liquidName);
        if (item == null) {
            item = adventure.getPlayer().getItemFromInventoryByShortName(liquidName);
        }
        if (item != null && item instanceof Liquid) {
            Liquid liquid = (Liquid) item;
            int healthChange = liquid.getHealthPoints();
            if (healthChange > 0) {
                userInterface.healthChange(healthChange, liquid);
            } else if (healthChange < 0) {
                String input = userInterface.confirmDrinkingUnhealthyLiquid();
                if (input.equals("yes")) {
                    userInterface.healthDecreaseChange(healthChange, liquid);
                } else if (input.equals("no")) {
                    String action = userInterface.keepOrDropLiquidPrompt();
                    if (action.equals("keep")) {
                        userInterface.keepLiquidPrompt(liquid);
                    } else if (action.equals("drop")) {
                        adventure.getPlayer().dropItem(liquid, adventure, userInterface); // Drop the food item
                    } else {
                        userInterface.eatOrDrinkInvalidInputKeepOrDrop();
                    }
                } else {
                    userInterface.eatOrDrinkInvalidInputYesOrNo();
                }
            } else {
                userInterface.itemNotEdible();
            }
            adventure.getPlayer().removeFromInventory(liquid);
            if (adventure.getPlayer().getHealth() <= 0) {
                userInterface.liquidGameOver();
            }
        } else {
            userInterface.liquidNotFound();
        }
    }

    public void dropItem(Liquid liquid, Adventure adventure, UserInterface userInterface) {
        Player player = adventure.getPlayer();
        Room currentRoom = player.getCurrentRoom();
        currentRoom.addItems(liquid);
        player.removeFromInventory(liquid);
        userInterface.droppedItemPrompt(liquid);
    }

    public void playerCraftItems(UserInterface userInterface, Adventure adventure) {
        String name = userInterface.craftItemNamePrompt();
        if (name.isEmpty()) {
            userInterface.invalidCraftingName();
            return;
        }

        int weight = userInterface.promptItemWeight();

        Item newItem = new Item(name, weight);
        adventure.getPlayer().craftItem(newItem);
        userInterface.displayCraftingMessage(name, weight); // Display the crafting message using UserInterface method
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
            currentRoom.stopMusic();
            nextRoom.playMusic();
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
                MeleeWeapon meleeWeapon = (MeleeWeapon) equippedWeapon;
                int damageDealt = meleeWeapon.getDamage();
                Room currentRoom = player.getCurrentRoom();
                ArrayList<Enemy> enemies = currentRoom.getEnemies();
                if (!enemies.isEmpty()) {
                    Enemy enemy = enemies.get(0);
                    if (enemy.isVulnerableToWeapon(equippedWeapon.getName())) {
                        enemy.takeDamage(damageDealt);
                        if (enemy.isDefeated()) {
                            if (enemy.getName().equals("Goblin King")) {
                                currentRoom.addItems(new MeleeWeapon("King David's Dagger", 50, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Putin")) {
                                currentRoom.addItems(new RangedWeapon("Putin's Bazooka", 50, 10, 10000, currentRoom));
                            }
                            if (enemy.getName().equals("Putin")) {
                                currentRoom.addItems(new RangedWeapon("Putin's Bazooka", 50, 10000, 10000, currentRoom));
                            }
                            if (enemy.getName().equals("H.C. Andersen")) {
                                currentRoom.addItems(new MeleeWeapon("Danmarks Våben", 70, 4500, currentRoom));
                            }
                            if (enemy.getName().equals("Satan")) {
                                currentRoom.addItems(new RangedWeapon("The Devils Flamethrower", 50, 20, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Unicornious")) {
                                currentRoom.addItems(new MeleeWeapon("Unicon Sword", 20, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Tarzan")) {
                                currentRoom.addItems(new MeleeWeapon("Tarzan's Spear", 70, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Harley Quinn")) {
                                currentRoom.addItems(new MeleeWeapon("King Kong's Fist", 60, 5500, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Bat", 70, 2500, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Harley Quinn's Joke Gun", 200, 15, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Mars Alien")) {
                                currentRoom.addItems(new RangedWeapon("Magnetic Railgun", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 8000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Atomic Bomb", 35, 5000, 15000, currentRoom));
                            }
                            if (enemy.getName().equals("Batman")) {
                                currentRoom.addItems(new RangedWeapon("Batman's Batarang", 20, 5, 500, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Batman's Batknife", 30, 2500, currentRoom));
                            }
                            if (enemy.getName().equals("Ricardo Diaz")) {
                                currentRoom.addItems(new RangedWeapon("Vice City Shotgun", 100, 10, 6000, currentRoom));
                            }
                            if (enemy.getName().equals("The Joker")) {
                                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Hammer", 100, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Traitor Lord")) {
                                currentRoom.addItems(new MeleeWeapon("King David's Sword", 300, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("The Pharaoh")) {
                                currentRoom.addItems(new MeleeWeapon("Pharaoh's Scepter", 80, 4000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Sandstorm Blaster", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Goliath")) {
                                currentRoom.addItems(new MeleeWeapon("Staff of Moses", 100, 5000, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Sword of Goliath", 150, 6000, currentRoom));
                            }
                            if (enemy.getName().equals("Cyber Athlete")) {
                                currentRoom.addItems(new MeleeWeapon("Deceiver Killer Sword", 500, 7000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("X-Ray Rifle", 40, 50, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Zombie")) {
                                currentRoom.addItems(new MeleeWeapon("Cold Steel Rapier", 40, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Samael")) {
                                currentRoom.addItems(new MeleeWeapon("Sword of Angels", 300, 6000, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Spear of Destiny", 250, 5000, currentRoom));
                            }
                            if (enemy.getName().equals("Deceiver")) {
                                currentRoom.addItems(new MeleeWeapon("Zombie Killer Sword", 500, 5000, currentRoom));
                            }
                            currentRoom.removeEnemy(enemy);
                        }
                    } else {
                        UserInterface userInterface = new UserInterface();
                        userInterface.cannotAttackWithWeapon();
                    }
                    enemyAttack(enemy, player);
                } else {
                    UserInterface ui = new UserInterface();
                    ui.weaponNoEnemies();
                }
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
                            if (enemy.getName().equals("Goblin King")) {
                                currentRoom.addItems(new MeleeWeapon("King David's Dagger", 50, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Putin")) {
                                currentRoom.addItems(new RangedWeapon("Putin's Bazooka", 50, 10, 10000, currentRoom));
                            }
                            if (enemy.getName().equals("Putin")) {
                                currentRoom.addItems(new RangedWeapon("Putin's Bazooka", 50, 10000, 10000, currentRoom));
                            }
                            if (enemy.getName().equals("H.C. Andersen")) {
                                currentRoom.addItems(new MeleeWeapon("Danmarks Våben", 70, 4500, currentRoom));
                            }
                            if (enemy.getName().equals("Satan")) {
                                currentRoom.addItems(new RangedWeapon("The Devils Flamethrower", 50, 20, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Unicornious")) {
                                currentRoom.addItems(new MeleeWeapon("Unicon Sword", 20, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Tarzan")) {
                                currentRoom.addItems(new MeleeWeapon("Tarzan's Spear", 70, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Harley Quinn")) {
                                currentRoom.addItems(new MeleeWeapon("King Kong's Fist", 60, 5500, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Bat", 70, 2500, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Harley Quinn's Joke Gun", 200, 15, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Mars Alien")) {
                                currentRoom.addItems(new RangedWeapon("Magnetic Railgun", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 8000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Atomic Bomb", 35, 5000, 15000, currentRoom));
                            }
                            if (enemy.getName().equals("Batman")) {
                                currentRoom.addItems(new RangedWeapon("Batman's Batarang", 20, 5, 500, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Batman's Batknife", 30, 2500, currentRoom));
                            }
                            if (enemy.getName().equals("Ricardo Diaz")) {
                                currentRoom.addItems(new RangedWeapon("Vice City Shotgun", 100, 10, 6000, currentRoom));
                            }
                            if (enemy.getName().equals("The Joker")) {
                                currentRoom.addItems(new MeleeWeapon("Harley Quinn's Hammer", 100, 4000, currentRoom));
                            }
                            if (enemy.getName().equals("Traitor Lord")) {
                                currentRoom.addItems(new MeleeWeapon("King David's Sword", 300, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("The Pharaoh")) {
                                currentRoom.addItems(new MeleeWeapon("Pharaoh's Scepter", 80, 4000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("Sandstorm Blaster", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Goliath")) {
                                currentRoom.addItems(new MeleeWeapon("Staff of Moses", 100, 5000, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Sword of Goliath", 150, 6000, currentRoom));
                            }
                            if (enemy.getName().equals("Cyber Athlete")) {
                                currentRoom.addItems(new MeleeWeapon("Deceiver Killer Sword", 500, 7000, currentRoom));
                                currentRoom.addItems(new RangedWeapon("X-Ray Rifle", 40, 50, 7000, currentRoom));
                            }
                            if (enemy.getName().equals("Zombie")) {
                                currentRoom.addItems(new MeleeWeapon("Cold Steel Rapier", 40, 3500, currentRoom));
                            }
                            if (enemy.getName().equals("Samael")) {
                                currentRoom.addItems(new MeleeWeapon("Sword of Angels", 300, 6000, currentRoom));
                                currentRoom.addItems(new MeleeWeapon("Spear of Destiny", 250, 5000, currentRoom));
                            }
                            if (enemy.getName().equals("Deceiver")) {
                                currentRoom.addItems(new MeleeWeapon("Zombie Killer Sword", 500, 5000, currentRoom));
                            }
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