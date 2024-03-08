package org.example;
public class Adventure {
    private Map map;
    private Player player;

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

}