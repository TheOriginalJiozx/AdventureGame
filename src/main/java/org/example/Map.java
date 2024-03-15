package org.example;

import javax.swing.*;

public class Map {

    private Room firstRoom;

    public Map() {
        this.firstRoom = createWorld();
    }

    public Room getFirstRoom() {
        return firstRoom;
    }

    private Room createWorld() {
        Room room1 = new Room("Room 1", "room with no distinct features. You see two doors. Choose wisely.\n");
        Room room2 = new Room("Room 2", "room with spiders crawling down the walls. There are two doors. They are crawling over to you, choose a door!\n");
        Room room3 = new Room("Room 3", "room with goblins eating dead dwarves. The lights are off, but you feel a torch! Enter 'torch' to use it.\n");
        Room room4 = new Room("Room 4", "room with dragons dancing. You see two doors. Don't worry, they only kill you if you run.\n");
        Room room5 = new Room("Room 5", "room full of gold and diamonds. Congratulations!\n");
        Room room6 = new Room("Room 6", "room with trolls ready to eat you. You see two doors. RUN!\n");
        Room room7 = new Room("Room 7", "room with a giant demonic bear. You see two doors. Play dead and pray or choose a door?\n");
        Room room8 = new Room("Room 8", "room with yellow slime everywhere. You see three doors. Choose a door.\n");
        Room room9 = new Room("Room 9", "room with giant rats ready to beat you down. You see two doors. I hope you brought giant traps with you. Choose a door!\n");


        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room1.addItems(new Item("Golden Key", "Key"));
        room1.addItems(new Food("Healthy Durum", "Durum", 100));
        room1.addItems(new RangedWeapon("Ali Babas AK47", "AK47", 30, 5));
        room1.addEnemy(new Enemy("Abraham Lincoln", 40, 10));

        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room2.addItems(new Item("Pink Revovler", "Revolver"));
        room2.addItems(new Food("Rotten dwarf-meat sandwich", "Sandwich", -10));
        room2.addItems(new MeleeWeapon("King David's Sword", "Sword", 100));

        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room3.addItems(new Item("Putin's Bunkerkey", "Suicide"));
        room3.addItems(new Item("Ultra Bomb Defuser", "Anti Bush"));
        room3.addItems(new Food("Singing soup made by Putin's chef", "Deadly Soup", -5));
        room3.addItems(new RangedWeapon("Putin's Bazooka", "Bazooka", 140, 5));
        room3.turnOnLights();
        room3.turnOffLights();

        room4.setNorthRoom(room1);
        room4.setSouthRoom(room7);
        room4.addItems(new Item("H.C. Andersen's Book", "The Ugly Duckling"));
        room4.addItems(new Food("Duck Soup", "Soup", 100));
        room4.addItems(new MeleeWeapon("Danmarks Våben", "Dannebrog", 25));

        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room6.addItems(new Item("The Devil's Image of his Trident", "Image of Devil's Trident"));
        room6.addItems(new Item("The Devils Flame Ring", "Flame Ring"));
        room6.addItems(new Food("Devil's Cheetos", "Cheetos", -300));
        room6.addItems(new RangedWeapon("The Devils Flamethrower", "Flamethrower", 666, 6));

        room7.setNorthRoom(room4);
        room7.setEastRoom(room8);
        room7.addItems(new Item("A Guitar made of Unicorns", "Unicorn Guitar"));
        room7.addItems(new Food("Unicorn Juice", "Juice", 5));
        room7.addItems(new MeleeWeapon("Unicon Sword", "Sword", 33));

        room8.setNorthRoom(room5);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room8.addItems(new Item("Tarzan's Rope", "Rope"));
        room8.addItems(new Food("Tarzan's Favorite Banana", "Banana", 50));
        room8.addItems(new MeleeWeapon("Tarzan's Spear", "Spear",100));

        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room9.addItems(new Item("George Bush's Bomb Manual", "Bomb Manual"));
        room9.addItems(new Food("Anti-Devil Potion", "Devil Killer", 400));
        room9.addItems(new MeleeWeapon("King Kong's Fist", "Devil Destroyer", 85));
        room9.addItems(new MeleeWeapon("Harley Quinn's Bat", "Quinns Bat", 100));
        room9.addItems(new RangedWeapon("Harley Quinn's Joke Gun", "Quinn's Joke Gun", 5, 10));
        room9.unlockWestRoom();
        room9.lockWestRoom();

        return room1;
    }
}