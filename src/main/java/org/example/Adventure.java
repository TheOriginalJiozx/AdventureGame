package org.example;
public class Adventure {
    public Player player;
    private Map map;
    public Room currentRoom;

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

    public Item dropItemFromInventory(String itemName) {
        return player.getCurrentRoom().dropItem(itemName.trim().toLowerCase());
    }

    public Item dropItemFromInventoryByShortName(String shortName) {
        for (Item item : player.getInventoryItems()) {
            if (item.getShortName().equalsIgnoreCase(shortName.trim().toLowerCase())) {
                player.getCurrentRoom().dropItem(item.getName());
                return item;
            }
        }
        return null;
    }

    public Room go(Direction direction) {
        return player.go(direction, new UserInterface());
    }

    public String unlockWestRoom() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Cave") || currentRoom.getName().equals("Desert")) {
            if (currentRoom.isWestRoomLocked()) {
                currentRoom.unlockWestRoom();
                return "West room unlocked!";
            }
        } else {
            return "You can only unlock the west room from Cave and Desert.";
        }
        return null;
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
            }
        } else {
            return "You can only unlock the west room from XRay Stadium, Manic Plains and Clown Town.";
        }
        return null;
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
            }
        } else {
            return "You can only unlock the north room from Vice City.";
        }
        return null;
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
            }
        } else {
            return "You can only unlock the south room from The Nile.";
        }
        return null;
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
        if (currentRoom.getName().equals("Mine Tunnels")) {
            if (currentRoom.areLightsOff()) {
                currentRoom.turnOnLights();
                return "The lights in the mine tunnels are now on!";
            } else {
                return "The lights in the mine tunnels are already on.";
            }
        } else {
            return "You can only turn on the lights in the Mine Tunnels.";
        }
    }

    public boolean tryTurnOnLights() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Mine Tunnels")) {
            return currentRoom.areLightsOff();
        }
        return false;
    }

    public String turnOffLightsRoom3() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Mine Tunnels")) {
            if (currentRoom.areLightsOn()) {
                currentRoom.turnOffLights();
                return "The lights in the mine tunnels are now off!";
            } else {
                return "The lights are already off in the mine tunnels.";
            }
        } else {
            return "You can only turn off the lights in the Mine Tunnels.";
        }
    }

    public boolean tryTurnOffLights() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom.getName().equals("Mine Tunnels")) {
            return currentRoom.areLightsOn();
        }
        return false;
    }

    public void handleXyzzy() {
        Room previousXyzzyPosition = getPlayer().teleportToXyzzyPosition();
        if (previousXyzzyPosition != null) {
            System.out.println("You have teleported to the previous xyzzy position.");
            Room currentRoom = previousXyzzyPosition;
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    public void handleUnlockRoom() {
        Room currentRoom = getPlayer().getCurrentRoom();
        boolean anyLocked = false;
        StringBuilder lockedRooms = new StringBuilder();

        if (currentRoom.isWestRoomLocked()) {
            anyLocked = true;
            lockedRooms.append("west, ");
        }
        if (currentRoom.isEastRoomLocked()) {
            anyLocked = true;
            lockedRooms.append("east, ");
        }
        if (currentRoom.isNorthRoomLocked()) {
            anyLocked = true;
            lockedRooms.append("north, ");
        }
        if (currentRoom.isSouthRoomLocked()) {
            anyLocked = true;
            lockedRooms.append("south, ");
        }

        if (anyLocked) {
            lockedRooms.setLength(lockedRooms.length() - 2);
        }

        if (anyLocked) {
            new UserInterface().roomsLocked(lockedRooms.toString());
        } else {
            new UserInterface().adjecentRoomsUnlocked();
        }
    }

}