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

            boolean playerHasAttacked = false;
            for (Enemy enemy : currentRoom.getEnemies()) {
                if (enemy.hasAttacked()) {
                    playerHasAttacked = true;
                    break;
                }
            }

            int playerHealthBeforeAction = getHealth();
            int playerHealthAfterAction;

            selectedWeapon.equip();
            userInterface.weaponEquippedMessage(selectedWeapon.getName());

            for (Item item : inventoryItems) {
                if (item instanceof Weapon && !item.equals(selectedWeapon)) {
                    ((Weapon) item).unequip();
                }
            }

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);

                playerHealthAfterAction = getHealth();

                UserInterface ui = new UserInterface();
                ui.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);

                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    ui.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
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
            boolean playerHasAttacked = false;
            for (Enemy enemy : currentRoom.getEnemies()) {
                if (enemy.hasAttacked()) {
                    playerHasAttacked = true;
                    break;
                }
            }

            int playerHealthBeforeAction = getHealth();
            int playerHealthAfterAction;

            int currentWeight = getInventoryWeight();
            int maxCarry = item.getMaxCarry();
            int itemWeight = item.getWeight();

            if (currentWeight + itemWeight > maxCarry) {
                userInterface.maxWeightPrompt();
            } else if (currentWeight + itemWeight == maxCarry) {
                addToInventory(item);
                userInterface.takenItemWarningPrompt(item);
            } else {
                addToInventory(item);
                userInterface.takenItemPrompt(item);
            }

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
                Enemy enemy = currentRoom.getEnemies().get(0);
                int damageDealtByEnemy = enemy.getDamage();
                decreaseHealth(damageDealtByEnemy);

                playerHealthAfterAction = getHealth();

                UserInterface ui = new UserInterface();
                ui.enemyAttacked(currentRoom.getEnemies().isEmpty() ? "" : currentRoom.getEnemies().get(0).getName(), damageDealtByEnemy, playerHealthBeforeAction, playerHealthAfterAction);

                if (!currentRoom.getEnemies().isEmpty() && currentRoom.getEnemies().get(0).getHealth() <= 0) {
                    ui.defeatedEnemy(currentRoom.getEnemies().get(0).getName());
                    currentRoom.removeEnemy(currentRoom.getEnemies().get(0));
                }
            }
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

        boolean playerHasAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy.hasAttacked()) {
                playerHasAttacked = true;
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

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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

        boolean playerHasAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy.hasAttacked()) {
                playerHasAttacked = true;
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

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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
        boolean playerHasAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy.hasAttacked()) {
                playerHasAttacked = true;
                break;
            }
        }

        currentRoom.addItems(food);
        removeFromInventory(food);
        userInterface.droppedItemPrompt(food);

        if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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

            boolean playerHasAttacked = false;
            int playerHealthBeforeAction = getHealth();
            for (Enemy enemy : currentRoom.getEnemies()) {
                if (enemy.hasAttacked()) {
                    playerHasAttacked = true;
                    break;
                }
            }

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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
        boolean playerHasAttacked = false;
        int playerHealthBeforeAction = getHealth();

        for (Enemy enemy : currentRoom.getEnemies()) {
            if (enemy.hasAttacked()) {
                playerHasAttacked = true;
                break;
            }
        }

        currentRoom.addItems(liquid);
        removeFromInventory(liquid);
        userInterface.droppedItemPrompt(liquid);

        if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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
            dropItem("Zeus Destroyer Component 1");
            dropItem("Zeus Destroyer Component 2");
            dropItem("Zeus Destroyer Component 3");
            dropItem("Zeus Destroyer Component 4");
            dropItem("Zeus Destroyer Component 5");

            RangedWeapon zeusDestroyer = new RangedWeapon("Zeus Destroyer", 5000, 10, 1, currentRoom);
            addToInventory(zeusDestroyer);

            userInterface.displayCraftingMessage("Zeus Destroyer", zeusDestroyer.getWeight());

            boolean playerHasAttacked = false;
            int playerHealthBeforeAction = getHealth();
            for (Enemy enemy : currentRoom.getEnemies()) {
                if (enemy.hasAttacked()) {
                    playerHasAttacked = true;
                    break;
                }
            }

            if (playerHasAttacked && !currentRoom.getEnemies().isEmpty()) {
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
    }

    public void attemptTheft(Thief thief, UserInterface userInterface) {
        Room currentRoom = getCurrentRoom();
        if (currentRoom.getThieves().contains(thief) && !getInventoryItems().isEmpty()) {
            thief.steal(this, userInterface);
            System.out.print(">> ");
        }
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
                ArrayList<Enemy> enemies = currentRoom.getEnemies();
                if (!enemies.isEmpty()) {
                    Enemy enemy = enemies.get(0);
                    enemy.takeDamage(damageDealt);
                    if (enemy.isDefeated()) {
                        currentRoom.enemyLoot(enemy, currentRoom);
                        currentRoom.removeEnemy(enemy);
                    }
                    enemyAttack(enemy, player, equippedWeapon);
                    enemy.playerAttacked();
                } else {
                    UserInterface ui = new UserInterface();
                    ui.weaponNoEnemies();
                }
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                if (rangedWeapon.getAmmonition() > 0) {
                    UserInterface ui = new UserInterface();
                    ui.weaponAmmonitionRemaining(rangedWeapon);
                    Room currentRoom = getCurrentRoom();
                    ArrayList<Enemy> enemies = currentRoom.getEnemies();
                    if (!enemies.isEmpty()) {
                        Enemy enemy = enemies.get(0);
                        int damageDealt = rangedWeapon.getDamage();
                        enemy.takeDamage(damageDealt);
                        if (enemy.isDefeated()) {
                            currentRoom.enemyLoot(enemy, currentRoom);
                            currentRoom.removeEnemy(enemy);
                        }
                        rangedWeapon.decreaseAmmonition();
                        enemyAttack(enemy, player, equippedWeapon);
                        enemy.playerAttacked();
                    } else {
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

    public void useWeaponNPC() {
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
                ArrayList<NPC> npcs = currentRoom.getNPCs();
                if (!npcs.isEmpty()) {
                    NPC npc = npcs.get(0);
                    if (npc.isVulnerableToWeapon(equippedWeapon.getName())) {
                        npc.takeDamage(damageDealt);
                        if (npc.isDefeated()) {
                            currentRoom.npcLoot(npc, currentRoom);
                            currentRoom.removeNPC(npc);
                        }
                    }
                    NPCAttack(npc, player, equippedWeapon);
                } else {
                    UserInterface ui = new UserInterface();
                    ui.weaponNoNPCs();
                }
            } else if (equippedWeapon instanceof RangedWeapon) {
                RangedWeapon rangedWeapon = (RangedWeapon) equippedWeapon;
                if (rangedWeapon.getAmmonition() > 0) {
                    UserInterface ui = new UserInterface();
                    ui.weaponAmmonitionRemaining(rangedWeapon);
                    Room currentRoom = getCurrentRoom();
                    ArrayList<NPC> npcs = currentRoom.getNPCs();
                    if (!npcs.isEmpty()) {
                        NPC npc = npcs.get(0);
                        int damageDealt = rangedWeapon.getDamage();
                        npc.takeDamage(damageDealt);
                        if (npc.isDefeated()) {
                            currentRoom.npcLoot(npc, currentRoom);
                            currentRoom.removeNPC(npc);
                        }
                        rangedWeapon.decreaseAmmonition();
                        NPCAttack(npc, player, equippedWeapon);
                    } else {
                        ui.weaponNoNPCs();
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
        if (playerHealthAfterAttack <= 0) {
            ui.gameOver();
        } else {
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
        if (playerHealthAfterAttack <= 0) {
            ui.gameOver();
        } else {
            ui.NPCAttacked(npc.getName(), damageDealtByNPC, playerHealthBeforeAttack, playerHealthAfterAttack);
            ui.PlayerNPCAttacked(npc.getName(), damageDealtByPlayer, npcHealthAfterAttack);
            if (npc.getHealth() <= 0) {
                ui.defeatedNPC(npc.getName());
            }
        }
    }
}