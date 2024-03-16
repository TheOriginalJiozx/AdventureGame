package org.example;

public class Map {

    private Room firstRoom;

    public Map() {
        this.firstRoom = createWorld();
    }

    public Room getFirstRoom() {
        return firstRoom;
    }

    private Room createWorld() {
        Room room1 = new Room("Desert", "You are in the middle of a flaming hot desert. The sun is burning on your skin. You feel dazed and you're on the brink of collapse caused be dehydration.\n");
        Room room2 = new Room("Goblin Camp", "The desert eventually ends and for miles a foul smell enters your nostrils. The disgusting smell of goblin murder and shit. You reach a giant camp. You enter. \n");
        Room room3 = new Room("Mine Tunnels", "You venture down the mine. Gradually all light disappears and suddenly you are lost.\n");
        Room room4 = new Room("Temple Ruins", "You arrive at a giant creepy forsaken temple. You enter.\n");
        Room room5 = new Room("King David's Room", "Finally you arrive on your lucky duck after travelling the sea for days. You have fallen in love with your duck and treasures beyond your wildest dreams awaits you.\n");
        Room room6 = new Room("Hell", "As you venure deeper and deeper into the mine everything becomes unbearable hot. You reach an elevator that only leads to the -666 floor. Oh shit. You press the button and descend.\n");
        Room room7 = new Room("Manic Plains", "You go through the secret passage and come out on the other site to a large plain with trolls everywhere.\n");
        Room room8 = new Room("Coast", "You arrive at the coast spotting a sad lonely duck.\n");
        Room room9 = new Room("Cave", "You enter a cave with giant rats ready to beat you down. I hope you brought giant traps with you.\n");
        Room room10 = new Room("Mars", "You stand on the rust-colored surface of Mars, surrounded by barren terrain and towering red rocks. The air is thin and chilly, and the sky above is a dusty pink. A sense of isolation and wonder fills your being as you gaze out at this alien landscape.\n");
        Room room11 = new Room("Gotham City", "Welcome to the iconic streets of Gotham City, where darkness reigns and danger lurks around every corner. Skyscrapers pierce the skyline, casting ominous shadows over the bustling cityscape. Prepare yourself for encounters with both heroes and villains in this gritty urban landscape.\n");
        Room room12 = new Room("Vice City", "You find yourself in the heart of Vice City, where neon lights flicker and palm trees sway against the backdrop of towering skyscrapers.\n");
        Room room13 = new Room("Clown Town", "Welcome to a whimsical yet eerie place filled with colorful circus tents and playful decorations. Beneath the cheerful facade lurks an unsettling atmosphere, as if the laughter of the clowns hides darker secrets.\n");
        Room room14 = new Room("Bomb Town", "Welcome to a perilous landscape where danger looms around every corner.\n");
        Room room15 = new Room("Gold Mine", "Welcome to a cavernous expanse filled with glimmering treasures waiting to be discovered.\n");
        Room room16 = new Room("The Nile", "Welcome to a mystical river shrouded in ancient secrets and untold mysteries.\n");
        Room room17 = new Room("Jerusalem", "Welcome to a city steeped in history, culture, and religious significance.\n");
        Room room18 = new Room("XRay Stadium", "Welcome to a futuristic arena where technology and entertainment collide.\n");
        Room room19 = new Room("Zombie Room", "You have entered a chilling chamber filled with the moans of the undead and the stench of decay.\n");
        Room room20 = new Room("Eden's Garden", "Behold! You have entered a sanctuary where nature's whispers paint the canvas of serenity with vibrant hues and tranquil melodies.\n");
        Room room21 = new Room("The Room of Deception", "You have stepped incautiously into a realm where illusions reign supreme and reality becomes but a fleeting whisper.");

        //Desert
        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room1.setWestRoom(room14);
        room1.addItems(new Item("Golden Key", "Key"));
        room1.addItems(new Food("Healthy Durum", "Durum", 100));
        room1.addItems(new RangedWeapon("Ali Babas AK47", "AK47", 30, 5));
        room1.addEnemy(new Enemy("Abraham Lincoln", 40, 10));
        room1.unlockWestRoom();
        room1.lockWestRoom();

        //Gobling Camp
        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room2.addItems(new Item("Pink Revovler", "Revolver"));
        room2.addItems(new Food("Rotten dwarf-meat sandwich", "Sandwich", -10));
        room2.addItems(new MeleeWeapon("King David's Sword", "Sword", 105));

        //Mine Tunnels
        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room3.setEastRoom(room11);
        room3.addItems(new Item("Putin's Bunkerkey", "Suicide"));
        room3.addItems(new Item("Ultra Bomb Defuser", "Anti Bush"));
        room3.addItems(new Food("Singing soup made by Putin's chef", "Soup", -5));
        room3.addItems(new RangedWeapon("Putin's Bazooka", "Bazooka", 140, 5));
        room3.addEnemy(new Enemy("Putin", 400, 50));
        room3.turnOnLights();
        room3.turnOffLights();

        //Temple Ruins
        room4.setNorthRoom(room1);
        room4.setSouthRoom(room7);
        room4.setWestRoom(room17);
        room4.addItems(new Item("H.C. Andersen's Book", "The Ugly Duckling"));
        room4.addItems(new Item("Giant Rat Trap", "Rat Trap"));
        room4.addItems(new Food("Duck Soup", "Soup", 100));
        room4.addItems(new MeleeWeapon("Danmarks Våben", "Dannebrog", 250));
        room4.addEnemy(new Enemy("H.C. Andersen", 200, 50));

        //King David's Room (room 5)

        //Hell
        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room6.addItems(new Item("The Devil's Image of his Trident", "Image of Devil's Trident"));
        room6.addItems(new Item("The Devils Flame Ring", "Flame Ring"));
        room6.addItems(new Food("Devil's Cheetos", "Cheetos", -300));
        room6.addItems(new RangedWeapon("The Devils Flamethrower", "Flamethrower", 66, 6));
        room6.addEnemy(new Enemy("Satan", 666, 666));

        //Manic Plains
        room7.setNorthRoom(room4);
        room7.setEastRoom(room8);
        room7.addItems(new Item("A Guitar made of Unicorns", "Unicorn Guitar"));
        room7.addItems(new Food("Unicorn Bread", "Bread", 5));
        room7.addItems(new MeleeWeapon("Unicon Sword", "Sword", 33));
        room7.addEnemy(new Enemy("Unicornious", 350, 140));
        room7.unlockEastRoom();
        room7.lockEastRoom();

        //Coast
        room8.setNorthRoom(room5);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room8.setSouthRoom(room12);
        room8.addItems(new Item("Tarzan's Rope", "Rope"));
        room8.addItems(new Food("Tarzan's Favorite Banana", "Banana", 50));
        room8.addItems(new MeleeWeapon("Tarzan's Spear", "Spear",100));
        room8.addEnemy(new Enemy("Tarzan", 500, 300));

        //Cave
        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room9.setSouthRoom(room10);
        room9.addItems(new Item("George Bush's Bomb Manual", "Bomb Manual"));
        room9.addItems(new Food("Anti-Batman Potion", "Potion", 600));
        room9.addItems(new MeleeWeapon("King Kong's Fist", "Fist", 85));
        room9.addItems(new MeleeWeapon("Harley Quinn's Bat", "Quinn's Bat", 100));
        room9.addItems(new RangedWeapon("Harley Quinn's Joke Gun", "Quinn's Joke Gun", 500, 10));
        room9.addEnemy(new Enemy("Harley Quinn", 333, 333));
        room9.unlockWestRoom();
        room9.lockWestRoom();

        //Mars
        room10.setNorthRoom(room9);
        room10.addItems(new Item("Red Stone", "Stone"));
        room10.addItems(new Food("Mars Bar", "Bar", -45));
        room10.setTries(Integer.MAX_VALUE);
        room10.addItems(new RangedWeapon("Magnetic Railgun", "Railgun", 400, RangedWeapon.INFINITE_AMMO_CAPACITY));
        room10.addItems(new RangedWeapon("Atomic Bomb", "Bomb", 35, 1));
        room10.addEnemy(new Enemy("Mars Alien", 5000, 300));

        //Gotham City
        room11.setWestRoom(room3);
        room11.setSouthRoom(room15);
        room11.addItems(new Item("Batman's Car", "Batmobile"));
        room11.addItems(new Food("Fruit Bat Food", "Bat Food", 300));
        room11.addItems(new RangedWeapon("Batman's Batarang", "Batarang", 30, 7));
        room11.addItems(new MeleeWeapon("Batman's Batknife", "Batknife", 50));
        room11.addEnemy(new Enemy("Batman", 600, 400));

        //Vice City
        room12.setNorthRoom(room8);
        room12.setEastRoom(room13);
        room12.setWestRoom(room18);
        room12.addItems(new Item("Vice City Plaque", "Plaque"));
        room12.addItems(new Item("George Bush's Bomb Defuser", "Bomb Defuser"));
        room12.addItems(new Food("Vice City Hotdog", "Hotdog", 66));
        room12.addItems(new RangedWeapon("Vice City Shotgun", "Shotgun", 190, 32));
        room12.addEnemy(new Enemy("Ricardo Diaz", 700, 550));
        room12.unlockNorthRoom();
        room12.lockNorthRoom();

        //Clown Town
        room13.setWestRoom(room12);
        room13.setEastRoom(room20);
        room13.addItems(new Item("Red Clown Nose", "Clown Nose"));
        room13.addItems(new Food("Apple Pie", "Pie", -55));
        room13.addItems(new MeleeWeapon("Harley Quinn's Hammer", "Quinn's Hammer", 150));
        room13.addEnemy(new Enemy("The Joker", 1000, 630));
        room13.addEnemy(new Enemy("The Clown from IT", 3000, 200));
        room13.unlockEastRoom();
        room13.lockEastRoom();

        //Bomb Town
        room14.setEastRoom(room1);
        room14.setSouthRoom(room17);
        room14.addItems(new Item("Golden Key to King David's Room", "The King's Key"));
        room14.addItems(new Food("Rosca de Reyes", "Three Kings Bread", 5000));
        room14.addItems(new MeleeWeapon("Sword of Gold", "Golden Sword", 1000));
        room14.addEnemy(new Enemy("Judas", 800, 0));

        //Gold Mine
        room15.setNorthRoom(room11);
        room15.setSouthRoom(room16);
        room15.addItems(new Item("Crown of Gold", "Crown"));
        room15.addItems(new Food("Purple Grapes", "Grapes", 50));
        room15.addItems(new MeleeWeapon("King David's Sword", "The King's Sword", 800));
        room15.addEnemy(new Enemy("Traitor Lord", 1000, 300));

        //The Nile
        room16.setNorthRoom(room15);
        room16.setSouthRoom(room20);
        room16.unlockSouthRoom();
        room16.lockSouthRoom();

        //Jerusalem
        room17.setNorthRoom(room14);
        room17.setEastRoom(room4);
        room17.setSouthRoom(room21);

        //XRay Stadium
        room18.setWestRoom(room19);
        room18.setEastRoom(room12);
        room18.unlockEastRoom();
        room18.lockEastRoom();

        //Zombie Room
        room19.setEastRoom(room18);

        //Edens Garden
        room20.setNorthRoom(room16);
        room20.setWestRoom(room13);

        //The Room of Deception
        room21.setNorthRoom(room17);
        room21.addItems(new Item("Gold", "Coal"));
        room21.addItems(new Item("Sword", "Rotten Zombie Fingers"));
        room21.addItems(new Food("Brownies", "Square Rat Poop", -300));
        room21.addItems(new MeleeWeapon("Zombie Killer Sword", "ZK Sword", 500));
        room21.addEnemy(new Enemy("Zombie 1", 500, 30));
        room21.addEnemy(new Enemy("Zombie 2", 500, 30));
        room21.addEnemy(new Enemy("Zombie 3", 500, 30));
        room21.addEnemy(new Enemy("Zombie 4", 500, 30));

        return room1;
    }
}