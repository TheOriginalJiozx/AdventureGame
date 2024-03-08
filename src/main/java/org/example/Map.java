package org.example;
public class Map {
    private Room startingRoom;

    public Map(){
        createRooms();
    }
    private void createRooms() {

        Room room1 = new Room("Desert", "You are in the middle of a flaming hot desert. The sun is burning on your skin. You feel dazed and you're on the brink of collapse caused be dehydration.");
        Room room2 = new Room("Goblin camp", "The desert eventually ends and for miles a foul smell enters your nostrils. The disgusting smell of goblin murder and shit. \n" +
                "\n" +
                "You reach a giant camp. You enter. ");
        Room room3 = new Room("Mine tunnels", "...");
        Room room4 = new Room("Temple ruins", "...");
        Room room5 = new Room("Famous treasure chamber", "...");
        Room room6 = new Room("Hell", "...");
        Room room7 = new Room("Manic plains", "...");
        Room room8 = new Room("Coast", "...");
        Room room9 = new Room("Heaven", "...");

        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room1.addItem(new Item("Key"));

        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room2.addItem(new Item("Pink revolver"));

        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);

        room4.setNorthRoom(room1);
        room4.setSouthRoom(room7);

        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);

        room7.setNorthRoom(room4);
        room7.setEastRoom(room8);

        room8.setNorthRoom(room5);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);

        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);


        startingRoom = room1;
    }
    public Room getStartingRoom(){
        return startingRoom;
    }
}