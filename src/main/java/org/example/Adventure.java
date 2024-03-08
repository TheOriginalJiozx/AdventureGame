package org.example;

public class Adventure {
    private Map map;
    public Player player;

    public Adventure() {
        map = new Map();
        player = new Player(map.getStartingRoom());
    }

    public void move(String direction){
        player.move(direction);
    }

    public void lookAround(){
        player.lookAround();
    }

    public void takeItem(String itemName){
        player.takeItem(itemName);
    }

    public void dropItem(String itemName){
        player.dropItem(itemName);
    }

    public void showInventory(){
        player.showInventory();
    }

    public void helpUser(Room currentRoom) {
        System.out.println("You are in " + currentRoom.getName());
        System.out.println("Description: " + currentRoom.getDescription());
        System.out.println("Commands:");
        System.out.println(commands());
    }

    private String commands() {
        StringBuilder commandList = new StringBuilder();
        commandList.append("'go north or n' to go north\n");
        commandList.append("'go south or s' to go south\n");
        commandList.append("'go east or e' to go east\n");
        commandList.append("'go west or w' to go west\n");
        commandList.append("'look' to look around\n");
        commandList.append("'help' if you forgot which room you are in\n");
        commandList.append("'exit' to exit program\n");
        commandList.append("'inventory' to open your inventory\n");
        commandList.append("'take' to pick up item\n");
        commandList.append("'drop' to drop item\n");
        return commandList.toString();
    }
}