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
        Room room18 = new Room("X-Ray Stadium", "Welcome to a futuristic arena where technology and entertainment collide.\n");
        Room room19 = new Room("Zombie Room", "You have entered a chilling chamber filled with the moans of the undead and the stench of decay.\n");
        Room room20 = new Room("Eden's Garden", "Behold! You have entered a sanctuary where nature's whispers paint the canvas of serenity with vibrant hues and tranquil melodies.\n");
        Room room21 = new Room("The Room of Deception", "You have stepped incautiously into a realm where illusions reign supreme and reality becomes but a fleeting whisper.");

        //Desert
        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room1.setWestRoom(room14);
        room1.addItems(new Item("Golden Key", "Key", 10));
        room1.addItems(new Food("Healthy Durum", "Durum", 30, 150));
        room1.addItems(new RangedWeapon("Ali Baba's AK47", "AK47", 20, 20, 4500));
        room1.addEnemy(new Enemy("Abraham Lincoln", 200, 40));
        room1.unlockWestRoom();
        room1.lockWestRoom();

        //Gobling Camp
        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room2.addItems(new Item("Pink Revolver", "Revolver", 1200));
        room2.addItems(new Food("Rotten dwarf-meat sandwich", "Sandwich", -10, 100));
        room2.addItems(new MeleeWeapon("King David's Sword", "Sword", 50, 3500));

        //Mine Tunnels
        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room3.setEastRoom(room11);
        room3.addItems(new Item("Putin's Bunkerkey", "Suicide", 10));
        room3.addItems(new Item("Ultra Bomb Defuser", "Anti Bush", 200));
        room3.addItems(new Food("Singing soup by Putin's chef", "Soup", 20, 400));
        room3.addItems(new RangedWeapon("Putin's Bazooka", "Bazooka", 50, 10, 6000));
        room3.addEnemy(new Enemy("Putin", 300, 30));
        room3.turnOnLights();
        room3.turnOffLights();

        //Temple Ruins
        room4.setNorthRoom(room1);
        room4.setSouthRoom(room7);
        room4.setWestRoom(room17);
        room4.addItems(new Item("H.C. Andersen's Book", "The Ugly Duckling", 300));
        room4.addItems(new Item("Giant Rat Trap", "Rat Trap", 1000));
        room4.addItems(new Food("Duck Soup", "Soup", 40, 400));
        room4.addItems(new MeleeWeapon("Danmarks Våben", "Dannebrog", 70, 4500));
        room4.addEnemy(new Enemy("H.C. Andersen", 150, 20));

        //King David's Room (room 5)

        //Hell
        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room6.addItems(new Item("The Devil's Image of his Trident", "Image of Devil's Trident", 2500));
        room6.addItems(new Item("The Devils Flame Ring", "Flame Ring", 1800));
        room6.addItems(new Food("Devil's Cheetos", "Cheetos", -50, 80));
        room6.addItems(new RangedWeapon("The Devils Flamethrower", "Flamethrower", 50, 20, 7000));
        room6.addEnemy(new Enemy("Satan", 666, 100));
        room6.addEnemy(new Enemy("Satan", 666, 100));

        //Manic Plains
        room7.setNorthRoom(room4);
        room7.setEastRoom(room8);
        room7.addItems(new Item("A Guitar made of Unicorns", "Unicorn Guitar", 4000));
        room7.addItems(new Food("Unicorn Bread", "Bread", 5, 150));
        room7.addItems(new MeleeWeapon("Unicon Sword", "Sword", 20, 4000));
        room7.addEnemy(new Enemy("Unicornious", 200, 30));
        room7.unlockEastRoom();
        room7.lockEastRoom();

        //Coast
        room8.setNorthRoom(room5);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room8.setSouthRoom(room12);
        room8.addItems(new Item("Tarzan's Rope", "Rope", 1000));
        room8.addItems(new Food("Tarzan's Favorite Banana", "Banana", 30, 100));
        room8.addItems(new MeleeWeapon("Tarzan's Spear", "Spear", 70, 3500));
        room8.addEnemy(new Enemy("Tarzan", 300, 40));
        room8.unlockNorthRoom(); // Unlocking the northRoom
        room8.lockNorthRoom();

        //Cave
        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room9.setSouthRoom(room10);
        room9.addItems(new Item("George Bush's Bomb Manual", "Bomb Manual", 800));
        room9.addItems(new Food("Anti-Batman Potion", "Potion", 50, 120));
        room9.addItems(new MeleeWeapon("King Kong's Fist", "Fist", 60, 5500));
        room9.addItems(new MeleeWeapon("Harley Quinn's Bat", "Quinn's Bat", 70, 2500));
        room9.addItems(new RangedWeapon("Harley Quinn's Joke Gun", "Quinn's Joke Gun", 200, 15, 4000));
        room9.addEnemy(new Enemy("Harley Quinn", 400, 50));
        room9.unlockWestRoom();
        room9.lockWestRoom();

        //Mars
        room10.setNorthRoom(room9);
        room10.setAmmonition(Integer.MAX_VALUE);
        room10.addItems(new Item("Red Stone", "Stone", 300));
        room10.addItems(new Food("Mars Bar", "Bar", -30, 40));
        room10.addItems(new RangedWeapon("Magnetic Railgun", "Railgun", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 8000));
        room10.addItems(new RangedWeapon("Atomic Bomb", "Bomb", 35, 1, 15000));
        room10.addItems(new Food("Mars Bar", "Bar", -30, 40));
        room10.addEnemy(new Enemy("Mars Alien", 1500, 200));

        //Gotham City
        room11.setWestRoom(room3);
        room11.setSouthRoom(room15);
        room11.addItems(new Item("Batman's Car", "Batmobile", 15000));
        room11.addItems(new Food("Fruit Bat Food", "Bat Food", 100, 150));
        room11.addItems(new RangedWeapon("Batman's Batarang", "Batarang", 20, 5, 500));
        room11.addItems(new MeleeWeapon("Batman's Batknife", "Batknife", 30, 2500));
        room11.addEnemy(new Enemy("Batman", 400, 40));

        //Vice City
        room12.setNorthRoom(room8);
        room12.setEastRoom(room13);
        room12.setWestRoom(room18);
        room12.addItems(new Item("Vice City Plaque", "Plaque", 500));
        room12.addItems(new Item("George Bush's Bomb Defuser", "Bomb Defuser", 1200));
        room12.addItems(new Food("Vice City Hotdog", "Hotdog", 30, 150));
        room12.addItems(new RangedWeapon("Vice City Shotgun", "Shotgun", 100, 10, 6000));
        room12.addEnemy(new Enemy("Ricardo Diaz", 500, 70));
        room12.unlockNorthRoom();
        room12.lockNorthRoom();

        //Clown Town
        room13.setWestRoom(room12);
        room13.setEastRoom(room20);
        room13.addItems(new Item("Red Clown Nose", "Clown Nose", 100));
        room13.addItems(new Food("Apple Pie", "Pie", -20, 200));
        room13.addItems(new MeleeWeapon("Harley Quinn's Hammer", "Quinn's Hammer", 100, 4000));
        room13.addEnemy(new Enemy("The Joker", 800, 100));
        room13.addEnemy(new Enemy("The Clown from IT", 3000, 200));
        room13.unlockEastRoom();
        room13.lockEastRoom();

        //Bomb Town
        room14.setEastRoom(room1);
        room14.setSouthRoom(room17);
        room14.addItems(new Item("Golden Key to King David's Room", "The King's Key", 50));
        room14.addItems(new Food("Rosca de Reyes", "Three Kings Bread", 100, 300));
        room14.addItems(new MeleeWeapon("Sword of Gold", "Golden Sword", 200, 6000));
        room14.addEnemy(new Enemy("Judas", 400, 50));

        //Gold Mine
        room15.setNorthRoom(room11);
        room15.setSouthRoom(room16);
        room15.addItems(new Item("Crown of Gold", "Crown", 1000));
        room15.addItems(new Food("Purple Grapes", "Grapes", 20, 150));
        room15.addItems(new MeleeWeapon("King David's Sword", "The King's Sword", 300, 7000));
        room15.addEnemy(new Enemy("Traitor Lord", 500, 70));

        //The Nile
        room16.setNorthRoom(room15);
        room16.setSouthRoom(room20);
        room16.setAmmonition(Integer.MAX_VALUE);
        room16.addItems(new Item("Key to Heaven", "Heaven's Key", 50));
        room16.addItems(new Food("Salmon Fish", "Fish", 20, 150));
        room16.addItems(new MeleeWeapon("Pharaoh's Scepter", "Scepter", 80, 4000));
        room16.addItems(new RangedWeapon("Sandstorm Blaster", "Blaster", 150, RangedWeapon.INFINITE_AMMO_CAPACITY, 7000));
        room16.addEnemy(new Enemy("The Pharaoh", 1000, 100));
        room16.unlockSouthRoom();
        room16.lockSouthRoom();

        //Jerusalem
        room17.addItems(new Item("Golden Chalice", "Chalice", 1000));
        room17.addItems(new Item("Ancient Scroll", "Scroll", 500));
        room17.addItems(new Food("Palestinian Manna", "Manna", 20, 150));
        room17.addItems(new Food("Palestinian Dates", "Dates", 40, 150));
        room17.addItems(new MeleeWeapon("Staff of Moses", "Moses' Staff", 100, 5000));
        room17.addItems(new MeleeWeapon("Sword of Goliath", "Goliath's Sword", 150, 6000));
        room17.addEnemy(new Enemy("Goliath", 500, 100));

        //X-Ray Stadium
        room18.setWestRoom(room19);
        room18.setEastRoom(room12);
        room18.addItems(new Item("X-Ray Glasses", "Glasses", 500));
        room18.addItems(new Food("Energy Bar", "Bar", 25, 150));
        room18.addItems(new Food("Sports Drink", "Drink", 15, 100));
        room18.addItems(new RangedWeapon("X-Ray Rifle", "Rifle", 40, 50, 7000));
        room18.addItems(new MeleeWeapon("Deceiver Killer Sword", "DK Sword", 500, 7000));
        room18.addEnemy(new Enemy("Cyber Athlete", 180, 20));
        room18.unlockEastRoom();
        room18.lockEastRoom();

        //Zombie Room
        room19.setEastRoom(room18);
        room19.addItems(new Item("XRay Glasses", "Glasses", 500));
        room19.addItems(new Item("Zombie Eyes", "EyeZ", 200));
        room19.addItems(new Food("Human Brain", "Brain", 100, 150));
        room19.addItems(new MeleeWeapon("Cold Steel Rapier", "Rapier", 40, 3500));
        room19.addEnemy(new Enemy("Zombie", 500, 30));
        room19.addEnemy(new Enemy("Zombie 2", 500, 30));
        room19.addEnemy(new Enemy("Zombie 3", 500, 30));
        room19.addEnemy(new Enemy("Zombie 4", 500, 30));

        //Edens Garden
        room20.addItems(new Item("Ancient Relic", "Relic", 800));
        room20.addItems(new Item("Floral Bouquet", "Bouquet", 500));
        room20.addItems(new Food("Ambrosia", "Ambrosia", 100, 200));
        room20.addItems(new Food("Golden Apple", "Apple", 50, 150));
        room20.addItems(new MeleeWeapon("Sword of Angels", "Angel Sword", 300, 6000));
        room20.addItems(new MeleeWeapon("Spear of Destiny", "Destiny Spear", 250, 5000));
        room20.addEnemy(new Enemy("Lucifer", 1000, 200));

        //The Room of Deception
        room21.setNorthRoom(room17);
        room21.addItems(new Item("Gold", "Coal", 200));
        room21.addItems(new Item("Sword", "Rotten Zombie Fingers", 200));
        room21.addItems(new Food("Brownies", "Square Rat Poop", -300, 50));
        room21.addItems(new MeleeWeapon("Zombie Killer Sword", "ZK Sword", 500, 5000));
        room21.addEnemy(new Enemy("Deceiver", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 2", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 3", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 4", 500, 30));

        return room1;
    }
}