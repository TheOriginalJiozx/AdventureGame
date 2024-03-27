package org.example;

import java.util.*;

public class Player {
    private Room currentRoom;
    private Room previousRoom;
    private ArrayList<Item> inventoryItems;
    private int health;
    private Room xyzzyRoom;
    private Music music;
    private Room teleportRoom;
    private boolean hasAttacked;
    private Thief thief;
    private Timer theftTimer;
    private boolean thiefInDesertSawWeapon = false;
    private boolean thiefScaredToStealWeapons = false;

    public Player(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.previousRoom = null;
        this.inventoryItems = new ArrayList<>();
        this.health = 250;
        this.xyzzyRoom = currentRoom;
        this.teleportRoom = null;
        this.hasAttacked = false;
        this.thief = null;
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

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void takeItem(UserInterface userInterface) {
        if (!userInterface.isLookDisplayed()) {
            System.out.println(userInterface.takeItemLookPrompt());
            return;
        }

        Item item = userInterface.enterItemNamePrompt();

        if (item != null) {
            int currentWeight = getInventoryWeight();
            int maxCarry = item.getMaxCarry();
            int itemWeight = item.getWeight();

            if (currentWeight + itemWeight > maxCarry) {
                userInterface.maxWeightPrompt();
                return;
            }

            if (!currentRoom.getThieves().isEmpty()) {
                Thief thief = currentRoom.getThieves().get(0);
                if (!currentRoom.isPlayerTookSomething()) {
                    currentRoom.setPlayerTookSomething(false);
                    if (theftTimer == null) {
                        startTheftTimer(thief, userInterface);
                    }
                }
                attemptTheft(thief, userInterface);
            }

            addToInventory(item);
            userInterface.takenItemPrompt(item);
            currentRoom.setPlayerTookSomething(true);
        }
    }

    private void startTheftTimer(Thief thief, UserInterface userInterface) {
        theftTimer = new Timer();
        theftTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!getInventoryItems().isEmpty()) {
                    attemptTheft(thief, userInterface);
                } else {
                    System.out.println("Thief has stopped attempting to steal as player's inventory is empty.");
                    stopTheftTimer();
                }
            }
        }, 7000, 7000);
    }

    private void stopTheftTimer() {
        if (theftTimer != null) {
            theftTimer.cancel();
            theftTimer = null;
        }
    }

    public void removeFromInventory(Item item) {
        inventoryItems.remove(item);
    }

    public void dropItem(UserInterface userInterface) {
        if (!userInterface.isViewInventory()) {
            userInterface.dropItemViewInventoryPrompt();
            return;
        }

        boolean normalEnemyAttacked = false;
        boolean passiveEnemyAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                normalEnemyAttacked = true;
                break;
            }
        }

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy instanceof PassiveEnemy && enemy.hasAttacked()) {
                passiveEnemyAttacked = true;
                break;
            }
        }

        Item itemName = userInterface.promptDropItemName();

        if (itemName != null) {
            Item removedItem = dropItem(itemName.getName());

            if (removedItem != null) {
                getCurrentRoom().addItems(removedItem);
                userInterface.droppedItemPrompt(removedItem);
            } else {
                userInterface.droppedItemNotFound();
            }

            if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);
                int playerHealthAfterAction = getHealth();
                userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
                }
            }
        }
    }

    public void eat(UserInterface userInterface) {
        if (!userInterface.isViewInventory()) {
            userInterface.eatViewInventoryPrompt();
            return;
        }

        boolean normalEnemyAttacked = false;
        boolean passiveEnemyAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                normalEnemyAttacked = true;
                break;
            }
        }

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy instanceof PassiveEnemy && enemy.hasAttacked()) {
                passiveEnemyAttacked = true;
                break;
            }
        }

        String foodName = userInterface.promptFoodName();
        Item item = getItemFromInventory(foodName);
        if (item == null) {
            item = getItemFromInventoryByShortName(foodName);
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
                        dropItem(food, userInterface);
                    } else {
                        userInterface.eatOrDrinkInvalidInputKeepOrDrop();
                    }
                } else {
                    userInterface.eatOrDrinkInvalidInputYesOrNo();
                }
            } else {
                userInterface.itemNotEdible();
            }
            removeFromInventory(food);
            if (getHealth() <= 0) {
                userInterface.foodGameOver();
            }

            if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);
                int playerHealthAfterAction = getHealth();
                userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
                }
            }
        } else {
            userInterface.foodNotFound();
        }
    }

    public void dropItem(Food food, UserInterface userInterface) {
        Room currentRoom = getCurrentRoom();
        boolean normalEnemyAttacked = false;
        boolean passiveEnemyAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                normalEnemyAttacked = true;
                break;
            }
        }

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy instanceof PassiveEnemy && enemy.hasAttacked()) {
                passiveEnemyAttacked = true;
                break;
            }
        }

        currentRoom.addItems(food);
        removeFromInventory(food);
        userInterface.droppedItemPrompt(food);

        if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
            Enemy enemy = currentRoom.getEnemies().get(0);
            int damageDealtByEnemy = enemy.getDamage();
            decreaseHealth(damageDealtByEnemy);
            int playerHealthAfterAction = getHealth();
            userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
            if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
            }
        }
    }

    public void drink(UserInterface userInterface) {
        if (!userInterface.isViewInventory()) {
            userInterface.liquidViewInventoryPrompt();
            return;
        }

        boolean normalEnemyAttacked = false;
        boolean passiveEnemyAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                normalEnemyAttacked = true;
                break;
            }
        }

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy instanceof PassiveEnemy && enemy.hasAttacked()) {
                passiveEnemyAttacked = true;
                break;
            }
        }

        String liquidName = userInterface.promptLiquidName();
        Item item = getItemFromInventory(liquidName);
        if (item == null) {
            item = getItemFromInventoryByShortName(liquidName);
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
                        dropItem(liquid, userInterface);
                    } else {
                        userInterface.eatOrDrinkInvalidInputKeepOrDrop();
                    }
                } else {
                    userInterface.eatOrDrinkInvalidInputYesOrNo();
                }
            } else {
                userInterface.itemNotEdible();
            }
            removeFromInventory(liquid);
            if (getHealth() <= 0) {
                userInterface.liquidGameOver();
            }

            if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);
                int playerHealthAfterAction = getHealth();
                userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
                }
            }
        } else {
            userInterface.liquidNotFound();
        }
    }

    public void dropItem(Liquid liquid, UserInterface userInterface) {
        Room currentRoom = getCurrentRoom();
        boolean normalEnemyAttacked = false;
        boolean passiveEnemyAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                normalEnemyAttacked = true;
                break;
            }
        }

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy instanceof PassiveEnemy && enemy.hasAttacked()) {
                passiveEnemyAttacked = true;
                break;
            }
        }

        currentRoom.addItems(liquid);
        removeFromInventory(liquid);
        userInterface.droppedItemPrompt(liquid);

        if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
            Enemy enemy = currentRoom.getEnemies().get(0);
            int damageDealtByEnemy = enemy.getDamage();
            decreaseHealth(damageDealtByEnemy);
            int playerHealthAfterAction = getHealth();
            userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
            if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
            }
        }
    }

    public void playerCraftItems(UserInterface userInterface) {
        boolean hasComponent1 = false;
        boolean hasComponent2 = false;
        for (Item item : getInventoryItems()) {
            if (item.getName().equals("Zeus Destroyer Component 1")) {
                hasComponent1 = true;
            }
            if (item.getName().equals("Zeus Destroyer Component 2")) {
                hasComponent2 = true;
            }
            if (item.getName().equals("Zeus Destroyer Component 3")) {
                hasComponent1 = true;
            }
            if (item.getName().equals("Zeus Destroyer Component 4")) {
                hasComponent2 = true;
            }
            if (item.getName().equals("Zeus Destroyer Component 5")) {
                hasComponent1 = true;
            }
        }

        if (hasComponent1 && hasComponent2) {
            boolean normalEnemyAttacked = false;
            int playerHealthBeforeAction = getHealth();

            for (Enemy enemy : currentRoom.getEnemies()) {
                if (!(enemy instanceof PassiveEnemy) && enemy.hasAttacked()) {
                    normalEnemyAttacked = true;
                    break;
                }
            }

            dropItem("Zeus Destroyer Component 1");
            dropItem("Zeus Destroyer Component 2");
            dropItem("Zeus Destroyer Component 3");
            dropItem("Zeus Destroyer Component 4");
            dropItem("Zeus Destroyer Component 5");

            RangedWeapon zeusDestroyer = new RangedWeapon("Zeus Destroyer", 5000, 10, 1, currentRoom);
            addToInventory(zeusDestroyer);

            userInterface.displayCraftingMessage("Zeus Destroyer", zeusDestroyer.getWeight());

            if (normalEnemyAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);
                int playerHealthAfterAction = getHealth();
                userInterface.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);
                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    userInterface.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
                }
            }
        } else {
            System.out.println("You do not have the required components to craft the weapon to destroy Zeus.");
        }
    }

    public int getHealth(){
        return health;
    }

    public Room go(Direction direction, UserInterface userInterface) {
        Room nextRoom = null;

        if (currentRoom.getName().equals("Mine Tunnels") && currentRoom.areLightsOff()) {
            // Special case for Mine Tunnels when lights are off
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
            // Normal movement
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
            // Stop music in current room
            currentRoom.stopMusic();

            // Play music in next room
            nextRoom.playMusic();

            // Display appropriate messages
            if (!nextRoom.hasVisited()) {
                userInterface.displayVisitedRoomMessage(nextRoom.getDescription(), nextRoom.getName(), nextRoom.getShortName());
                nextRoom.setVisited(true);
            } else {
                userInterface.displayReturnRoomMessage(nextRoom.getName());
            }

            // Update current and previous rooms
            previousRoom = currentRoom;
            currentRoom = nextRoom;

            // Enter the new room
            currentRoom.enterRoom(this, null, userInterface);
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

    public void handlePlayerThiefInteraction(Thief thief, UserInterface userInterface) {
        if (!inventoryItems.isEmpty()) {
            startThiefTimer(thief, userInterface);
        } else {
            System.out.println(thief.getName() + " is not attempting to steal because your inventory is empty.");
        }
    }

    private void startThiefTimer(Thief thief, UserInterface userInterface) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                attemptTheft(thief, userInterface);
            }
        }, 7000, 7000);

        Room initialRoom = getCurrentRoom();
        if (initialRoom.getThieves().contains(thief) && initialRoom.isPlayerTookSomething()) {
            thief.steal(this, userInterface);
            if (initialRoom.equals(getCurrentRoom())) {
                thiefInDesertSawWeapon = true;
            }
        }
    }

    public void attemptTheft(Thief thief, UserInterface userInterface) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom.getThieves().contains(thief)) {
            ArrayList<Item> inventoryItems = getInventoryItems();
            ArrayList<Item> nonWeaponItems = getNonWeaponItems();

            if (!nonWeaponItems.isEmpty()) {
                thief.steal(this, userInterface);
                thiefScaredToStealWeapons = false;
            } else if (!inventoryItems.isEmpty()) {
                boolean hasWeapon = false;
                for (Item item : inventoryItems) {
                    if (item instanceof Weapon) {
                        hasWeapon = true;
                        break;
                    }
                }
                if (hasWeapon) {
                    if (!thiefScaredToStealWeapons) {
                        System.out.println("The thief is attempting to steal from you but is scared to steal your weapon(s). He has informed the other thieves.");
                        thiefScaredToStealWeapons = true;
                    }
                } else {
                    thief.steal(this, userInterface);
                    thiefScaredToStealWeapons = false;
                }
            }
        }
    }

    private ArrayList<Item> getNonWeaponItems() {
        ArrayList<Item> nonWeaponItems = new ArrayList<>();
        for (Item item : getInventoryItems()) {
            if (!(item instanceof Weapon)) {
                nonWeaponItems.add(item);
            }
        }
        return nonWeaponItems;
    }

    private void transferItemToThiefIfNotEmpty(Thief thief, UserInterface userInterface) {
        int randomIndex = new Random().nextInt(inventoryItems.size());
        Item itemToTransfer = inventoryItems.remove(randomIndex);
        removeFromInventoryAndTransferToThief(itemToTransfer, thief, userInterface);
    }

    private void removeFromInventoryAndTransferToThief(Item item, Thief thief, UserInterface userInterface) {
        inventoryItems.remove(item);
        thief.addToInventory(item);
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

    public Room teleportToPosition(Room targetRoom) {
        Room previousRoom = currentRoom;
        currentRoom = targetRoom;
        return previousRoom;
    }

    public void setTeleportRoom(Room room) {
        this.teleportRoom = room;
    }

    public void useWeapon() {
        Weapon equippedWeapon = null;
        for (Item item : getInventoryItems()) {
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
                Room currentRoom = getCurrentRoom();
                ArrayList<Enemy> enemies = currentRoom.getEnemies();
                ArrayList<PassiveEnemy> passiveEnemies = currentRoom.getPassiveEnemies();
                if (!enemies.isEmpty()) {
                    for (Enemy enemy : enemies) {
                        int damageDealt = meleeWeapon.getDamage();
                        enemy.takeDamage(damageDealt);
                        if (enemy.isDefeated()) {
                            currentRoom.enemyLoot(enemy, currentRoom);
                            currentRoom.removeEnemy(enemy);
                        }
                        enemyAttack(enemy, this, equippedWeapon);
                    }
                } else if (!passiveEnemies.isEmpty()) {
                    for (PassiveEnemy passiveEnemy : passiveEnemies) {
                        int damageDealt = meleeWeapon.getDamage();
                        passiveEnemy.takeDamage(damageDealt);
                        if (passiveEnemy.isDefeated()) {
                            currentRoom.passiveEnemyLoot(passiveEnemy, currentRoom);
                            currentRoom.removePassiveEnemy(passiveEnemy);
                        }
                        enemyAttack(passiveEnemy, this, equippedWeapon);
                    }
                } else {
                    UserInterface ui = new UserInterface();
                    ui.noEnemiesInRoom();
                }
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                Room currentRoom = getCurrentRoom();
                ArrayList<Enemy> enemies = currentRoom.getEnemies();
                ArrayList<PassiveEnemy> passiveEnemies = currentRoom.getPassiveEnemies();
                if (!enemies.isEmpty()) {
                    Enemy enemy = enemies.get(0); // Assuming only one enemy can be targeted at a time
                    int damageDealt = rangedWeapon.getDamage();
                    if (enemy.getName().equals("Zeus") && !equippedWeapon.getName().equals("Zeus Destroyer")) {
                        // If Zeus is targeted and the weapon is not Zeus Destroyer, player takes damage but deals none
                        decreaseHealth(damageDealt); // Player takes damage
                        UserInterface ui = new UserInterface();
                        ui.enemyAttacked(enemy.getName(), damageDealt, getHealth(), getHealth() - damageDealt);
                    } else {
                        enemy.takeDamage(damageDealt); // Otherwise, damage is dealt normally
                        if (enemy.isDefeated()) {
                            currentRoom.enemyLoot(enemy, currentRoom);
                            currentRoom.removeEnemy(enemy);
                        }
                        rangedWeapon.decreaseAmmonition();
                        enemyAttack(enemy, this, equippedWeapon);
                    }
                } else if (!passiveEnemies.isEmpty()) {
                    PassiveEnemy passiveEnemy = passiveEnemies.get(0); // Assuming only one passive enemy can be targeted at a time
                    int damageDealt = rangedWeapon.getDamage();
                    passiveEnemy.takeDamage(damageDealt);
                    if (passiveEnemy.isDefeated()) {
                        currentRoom.passiveEnemyLoot(passiveEnemy, currentRoom);
                        currentRoom.removePassiveEnemy(passiveEnemy);
                    }
                    rangedWeapon.decreaseAmmonition();
                    enemyAttack(passiveEnemy, this, equippedWeapon);
                } else {
                    UserInterface ui = new UserInterface();
                    ui.noEnemiesInRoom();
                }
            }
        } else {
            UserInterface ui = new UserInterface();
            ui.weaponNotEquipped();
        }
    }

    public void useWeaponNPC() {
        Weapon equippedWeapon = null;
        for (Item item : getInventoryItems()) {
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
                Room currentRoom = getCurrentRoom();
                ArrayList<NPC> npcs = currentRoom.getNPCs();
                if (!npcs.isEmpty()) {
                    for (NPC npc : npcs) {
                        int damageDealt = meleeWeapon.getDamage();
                        npc.takeDamage(damageDealt);
                        if (npc.isDefeated()) {
                            currentRoom.npcLoot(npc, currentRoom);
                            currentRoom.removeNPC(npc);
                        }
                        NPCAttack(npc, this, equippedWeapon);
                    }
                } else {
                    UserInterface ui = new UserInterface();
                    ui.noNPCsInRoom();
                }
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                Room currentRoom = getCurrentRoom();
                ArrayList<NPC> npcs = currentRoom.getNPCs();
                if (!npcs.isEmpty()) {
                    NPC npc = npcs.get(0); // Assuming only one NPC can be targeted at a time
                    int damageDealt = rangedWeapon.getDamage();
                    npc.takeDamage(damageDealt); // Damage is dealt to NPC
                    if (npc.isDefeated()) {
                        currentRoom.npcLoot(npc, currentRoom);
                        currentRoom.removeNPC(npc);
                    }
                    rangedWeapon.decreaseAmmonition();
                    NPCAttack(npc, this, equippedWeapon);
                } else {
                    UserInterface ui = new UserInterface();
                    ui.noNPCsInRoom();
                }
            }
        } else {
            UserInterface ui = new UserInterface();
            ui.weaponNotEquipped();
        }
    }

    public void useWeaponThief() {
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
                Room currentRoom = getCurrentRoom();
                ArrayList<Thief> thieves = currentRoom.getThieves();
                if (!thieves.isEmpty()) {
                    Thief thief = thieves.get(0);
                    thief.takeDamage(damageDealt);
                    if (thief.isDefeated()) {
                        currentRoom.thiefLoot(thief, currentRoom, player);
                        currentRoom.removeThief(thief);
                    }
                    thiefAttack(thief, player, equippedWeapon);
                } else {
                    UserInterface ui = new UserInterface();
                    ui.noThievesInRoom();
                }
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                if (rangedWeapon.getAmmonition() > 0) {
                    Room currentRoom = getCurrentRoom();
                    ArrayList<Thief> thieves = currentRoom.getThieves();
                    if (!thieves.isEmpty()) {
                        Thief thief = thieves.get(0);
                        int damageDealt = rangedWeapon.getDamage();
                        thief.takeDamage(damageDealt);
                        if (thief.isDefeated()) {
                            currentRoom.thiefLoot(thief, currentRoom, player);
                            currentRoom.removeThief(thief);
                        }
                        rangedWeapon.decreaseAmmonition();
                        thiefAttack(thief, player, equippedWeapon);
                    } else {
                        UserInterface ui = new UserInterface();
                        ui.noThievesInRoom();
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

    public void enemyAttack(Enemy enemy, Player player, Weapon weapon) {
        int playerHealthBeforeAttack = player.getHealth();
        int damageDealtByEnemy = enemy.getDamage();
        int damageDealtByPlayer = weapon.getDamage();

        player.decreaseHealth(damageDealtByEnemy);
        int playerHealthAfterAttack = player.getHealth();
        int enemyHealthAfterAttack = enemy.getHealth();

        UserInterface ui = new UserInterface();
        ui.enemyAttacked(enemy.getName(), damageDealtByEnemy, playerHealthBeforeAttack, playerHealthAfterAttack);

        if (enemy.getHealth() <= 0) {
            ui.defeatedEnemy(enemy.getName());
        } else {
            ui.playerAttack(enemy.getName(), damageDealtByPlayer, enemyHealthAfterAttack);

            if (enemy.getHealth() <= 0) {
                ui.defeatedEnemy(enemy.getName());
            }

            if (enemy.getName().equals("Zeus") && enemyHealthAfterAttack <= 0) {
                ui.victory();
            }
        }

        // Check for negative health and trigger game over if necessary
        if (playerHealthAfterAttack <= 0) {
            ui.gameOver();
        }

        enemy.setHasAttacked(true);
    }

    private void NPCAttack(NPC npc, Player player, Weapon weapon) {
        int playerHealthBeforeAttack = player.getHealth();
        int damageDealtByNPC = npc.getDamage();
        int damageDealtByPlayer = weapon.getDamage();

        player.decreaseHealth(damageDealtByNPC);
        int playerHealthAfterAttack = player.getHealth();
        int npcHealthAfterAttack = npc.getHealth();

        UserInterface ui = new UserInterface();
        ui.NPCAttacked(npc.getName(), damageDealtByNPC, playerHealthBeforeAttack, playerHealthAfterAttack);
        ui.PlayerNPCAttacked(npc.getName(), damageDealtByPlayer, npcHealthAfterAttack);

        if (playerHealthAfterAttack <= 0) {
            ui.gameOver();
        } else if (npc.getHealth() <= 0) {
            ui.defeatedNPC(npc.getName());
        }
    }

    private void thiefAttack(Thief thief, Player player, Weapon weapon) {
        int playerHealthBeforeAttack = player.getHealth();
        int damageDealtByThief = thief.getDamage();
        int damageDealtByPlayer = weapon.getDamage();

        player.decreaseHealth(damageDealtByThief);
        int playerHealthAfterAttack = player.getHealth();
        int thiefHealthAfterAttack = thief.getHealth();

        UserInterface ui = new UserInterface();
        ui.ThiefAttacked(thief.getName(), damageDealtByThief, playerHealthBeforeAttack, playerHealthAfterAttack);
        ui.PlayerThiefAttacked(thief.getName(), damageDealtByPlayer, thiefHealthAfterAttack);

        if (playerHealthAfterAttack <= 0) {
            ui.gameOver();
        } else if (thief.getHealth() <= 0) {
            ui.defeatedThief(thief.getName());
        }
    }
}