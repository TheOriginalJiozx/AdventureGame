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
        if (currentRoom.getName().equals("Cave") || currentRoom.getName().equals("Desert")) {
            if (currentRoom.isWestRoomLocked()) {
                currentRoom.unlockWestRoom();
                return "West room unlocked!";
            } else {
                return "West room is already unlocked.";
            }
        } else {
            return "You can only unlock the west room from Cave and Desert.";
        }
    }

    public boolean tryUnlockWestRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Cave") || currentRoom.getName().equals("Desert")) {
            return currentRoom.isWestRoomLocked();
        }
        return false;
    }

    public String unlockEastRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("XRay Stadium") || currentRoom.getName().equals("Manic Plains") || currentRoom.getName().equals("Clown Town")) {
            if (currentRoom.isEastRoomLocked()) {
                currentRoom.unlockEastRoom();
                return "East room unlocked!";
            } else {
                return "East room is already unlocked.";
            }
        } else {
            return "You can only unlock the west room from XRay Stadium, Manic Plains and Clown Town.";
        }
    }

    public boolean tryUnlockEastRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("XRay Stadium") || currentRoom.getName().equals("Manic Plains") || currentRoom.getName().equals("Clown Town")) {
            return currentRoom.isEastRoomLocked();
        }
        return false;
    }

    public String unlockNorthRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Vice City")) {
            if (currentRoom.isNorthRoomLocked()) {
                currentRoom.unlockNorthRoom();
                return "North room unlocked!";
            } else {
                return "North room is already unlocked.";
            }
        } else {
            return "You can only unlock the north room from Vice City.";
        }
    }

    public boolean tryUnlockNorthRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Vice City")) {
            return currentRoom.isNorthRoomLocked();
        }
        return false;
    }

    public String unlockSouthRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("The Nile")) {
            if (currentRoom.isSouthRoomLocked()) {
                currentRoom.unlockSouthRoom();
                return "South room unlocked!";
            } else {
                return "South room is already unlocked.";
            }
        } else {
            return "You can only unlock the south room from The Nile.";
        }
    }

    public boolean tryUnlockSouthRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("The Nile")) {
            return currentRoom.isSouthRoomLocked();
        }
        return false;
    }

    public String turnOnLightsRoom3() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 3")) {
            if (currentRoom.areLightsOff()) {
                currentRoom.turnOnLights();
                return "The lights in room 3 are now on!";
            } else {
                return "The lights in room 3 are already on.";
            }
        } else {
            return "You can only turn on the lights in Room 3.";
        }
    }

    public boolean tryTurnOnLights() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 3")) {
            return currentRoom.areLightsOff();
        }
        return false;
    }

    public String turnOffLightsRoom3() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 3")) {
            if (currentRoom.areLightsOn()) {
                currentRoom.turnOffLights();
                return "The lights in room 3 are now off!";
            } else {
                return "The lights are already off in this room.";
            }
        } else {
            return "You can only turn off the lights in Room 3.";
        }
    }

    public boolean tryTurnOffLights() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Room 3")) {
            return currentRoom.areLightsOn();
        }
        return false;
    }
}