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
        Room room1 = new Room("Desert", "You are in the middle of a flaming hot desert. The sun is burning on your skin. You feel dazed and you're on the brink of collapse caused by dehydration.\n");
        Room room2 = new Room("Goblin Camp", "The desert eventually ends and for miles a foul smell enters your nostrils. The disgusting smell of goblin murder and shit. You reach a giant camp. You enter. \n");
        Room room3 = new Room("Mine Tunnels", "You venture down the mine. Gradually all light disappears and suddenly you are lost.\n");
        Room room4 = new Room("Temple Ruins", "You arrive at a giant creepy forsaken temple. You enter.\n");
        Room room5 = new Room("King David's Room", "Finally you arrive on your lucky duck after traveling the sea for days. You have fallen in love with your duck and treasures beyond your wildest dreams await you.\n");
        Room room6 = new Room("Hell", "As you venture deeper and deeper into the mine everything becomes unbearably hot. You reach an elevator that only leads to the -666 floor. Oh shit. You press the button and descend.\n");
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

        // Desert
        room1.setEastRoom(room2);
        room1.setSouthRoom(room4);
        room1.setWestRoom(room14);
        room1.addItems(new Item("Golden Key", 10));
        room1.addItems(new Item("Abraham Lincoln's Hat", 200));
        room1.addItems(new Food("Healthy Durum", -330, 150));
        room1.addItems(new Liquid("Faxe Kondi", -110, 500));
        room1.addItems(new RangedWeapon("Ali Baba's AK47", 20000, 40000, 4500, room1));
        room1.addEnemy(new Enemy("Abraham Lincoln", 200, 40));
        room1.addNPC(new NPC("Andrew Johnson", 100, 40));
        room1.addNPC(new NPC("Hannibal Hamlin", 100, 40));
        room1.unlockWestRoom();
        room1.lockWestRoom();
        room1.setMusic(new Music("DesertCaravan.wav"));

        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room2.addItems(new Item("Green Revolver Bullet", 1200));
        room2.addItems(new Food("Rotten dwarf-meat sandwich", -10, 100));
        room2.addItems(new Liquid("Goblin Juice", -20, 500));
        Enemy goblinKing = new Enemy("Goblin King", 250, 20);
        room2.addEnemy(goblinKing);
        room2.setMusic(new Music("GoblinSong.wav"));

        // Mine Tunnels
        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room3.setEastRoom(room11);
        room3.addItems(new Item("Putin's Bunker Key", 10));
        room3.addItems(new Item("Ultra Bomb Defuser", 200));
        room3.addItems(new Food("Singing soup by Putin's chef", 20, 400));
        room3.addEnemy(new Enemy("Putin", 300, 30));
        room3.addItems(new Liquid("Kvass", 20, 200));
        room3.turnOnLights();
        room3.turnOffLights();

        // Temple Ruins
        room4.setNorthRoom(room1);
        room4.setSouthRoom(room7);
        room4.setWestRoom(room17);
        room4.addItems(new Item("H.C. Andersen's Book", 300));
        room4.addItems(new Item("Giant Rat Trap", 1000));
        room4.addItems(new Food("Duck Soup", 40, 400));
        room4.addEnemy(new Enemy("H.C. Andersen", 150, 20));
        room4.addItems(new Liquid("Water", 30, 200));

        // King David's Room (room 5)
        room5.addEnemy(new Enemy("Zeus", 5000, 500));
        room5.setSouthRoom(room7);

        // Hell
        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room6.addItems(new Item("The Devil's Image of his Trident", 2500));
        room6.addItems(new Item("The Devils Flame Ring", 1800));
        room6.addItems(new Food("Devil's Cheetos", -50, 80));
        room6.addEnemy(new Enemy("Satan", 666, 100));
        room6.addItems(new Liquid("Hellfire", -6666, 10));

        // Manic Plains
        room7.setNorthRoom(room4);
        room7.setEastRoom(room8);
        room7.setSouthRoom(room18);
        room7.addItems(new Item("A Guitar made of Unicorns", 4000));
        room7.addItems(new Food("Unicorn Bread", 5, 150));
        room7.addEnemy(new Enemy("Unicornious", 200, 30));
        room7.addItems(new Liquid("Unicorn Juice", 200, 100));
        room7.unlockEastRoom();
        room7.lockEastRoom();

        // Coast
        room8.setNorthRoom(room5);
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room8.setSouthRoom(room12);
        room8.addItems(new Item("Tarzan's Rope", 1000));
        room8.addItems(new Food("Tarzan's Favorite Banana", 30, 100));
        room8.addEnemy(new Enemy("Tarzan", 300, 40));
        room8.addItems(new Liquid("Coconut Water", 120, 1000));
        room8.unlockNorthRoom();
        room8.lockNorthRoom();

        // Cave
        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room9.setSouthRoom(room10);
        room9.addItems(new Item("George Bush's Bomb Manual", 800));
        room9.addItems(new Food("Anti-Batman Potion", 50, 120));
        room9.addEnemy(new Enemy("Harley Quinn", 400, 50));
        room9.addItems(new Liquid("Pink and Blue Milkshake", -30, 500));
        room9.unlockWestRoom();
        room9.lockWestRoom();

        // Mars
        room10.setNorthRoom(room9);
        room10.setAmmonition(Integer.MAX_VALUE);
        room10.addItems(new Item("Red Stone", 300));
        room10.addItems(new Food("Mars Bar", -30, 40));
        room10.addItems(new Food("Martian Juice", -30, 40));
        room10.addEnemy(new Enemy("Mars Alien", 1500, 200));
        room10.addItems(new Liquid("Martian Water", 0, 500));

        // Gotham City
        room11.setWestRoom(room3);
        room11.setSouthRoom(room15);
        room11.addItems(new Item("Batman's Car", 15000));
        room11.addItems(new Food("Fruit Bat Food", 100, 150));
        room11.addEnemy(new Enemy("Batman", 400, 40));
        room11.addItems(new Liquid("Batman Juice", 10, 300));

        // Vice City
        room12.setNorthRoom(room8);
        room12.setEastRoom(room13);
        room12.setWestRoom(room18);
        room12.addItems(new Item("Vice City Plaque", 500));
        room12.addItems(new Item("George Bush's Bomb Defuser", 1200));
        room12.addItems(new Food("Vice City Hotdog", 30, 150));
        room12.addEnemy(new Enemy("Ricardo Diaz", 500, 70));
        room12.addItems(new Liquid("Vice City Soda", 5, 200));
        room12.unlockNorthRoom();
        room12.lockNorthRoom();

        // Clown Town
        room13.setWestRoom(room12);
        room13.setEastRoom(room20);
        room13.addItems(new Item("Red Clown Nose", 100));
        room13.addItems(new Food("Apple Pie", -20, 200));
        room13.addEnemy(new Enemy("The Joker", 800, 100));
        room13.addEnemy(new Enemy("The Clown from IT", 3000, 200));
        room13.addItems(new Liquid("Clown Tears", -20, 100));
        room13.unlockEastRoom();
        room13.lockEastRoom();

        // Bomb Town
        room14.setEastRoom(room1);
        room14.setSouthRoom(room17);
        room14.addItems(new Item("Golden Key to King David's Room", 50));
        room14.addItems(new Food("Rosca de Reyes", 100, 300));
        room14.addItems(new MeleeWeapon("Sword of Gold", 200, 6000, room14));
        room14.addEnemy(new Enemy("Judas", 400, 50));
        room14.addItems(new Liquid("Bomb Town Energy Drink", 15, 250));

        // Gold Mine
        room15.setNorthRoom(room11);
        room15.setSouthRoom(room16);
        room15.addItems(new Item("Crown of Gold", 1000));
        room15.addItems(new Food("Purple Grapes", 20, 150));
        room15.addEnemy(new Enemy("Traitor Lord", 500, 70));
        room15.addItems(new Liquid("Gold Mine Wine", 20, 400));

        // The Nile
        room16.setNorthRoom(room15);
        room16.setSouthRoom(room20);
        room16.setAmmonition(Integer.MAX_VALUE);
        room16.addItems(new Item("Key to Heaven", 50));
        room16.addItems(new Food("Salmon Fish", 20, 150));
        room16.addEnemy(new Enemy("The Pharaoh", 1000, 100));
        room16.addItems(new Liquid("Nile Water", 0, 500));
        room16.unlockSouthRoom();
        room16.lockSouthRoom();

        // Jerusalem
        room17.setEastRoom(room4);
        room17.setSouthRoom(room21);
        room17.addItems(new Item("Golden Chalice", 1000));
        room17.addItems(new Item("Ancient Scroll", 500));
        room17.addItems(new Food("Palestinian Manna", 20, 150));
        room17.addItems(new Food("Palestinian Dates", 40, 150));
        room17.addEnemy(new Enemy("Goliath", 500, 100));
        room17.addItems(new Liquid("Jerusalem Wine", 25, 300));

        // X-Ray Stadium
        room18.setWestRoom(room19);
        room18.setEastRoom(room12);
        room18.setNorthRoom(room7);
        room18.addItems(new Item("X-Ray Glasses", 500));
        room18.addItems(new Food("Energy Bar", 25, 150));
        room18.addItems(new Food("Sports Drink", 15, 100));
        room18.addEnemy(new Enemy("Cyber Athlete", 180, 20));
        room18.addItems(new Liquid("X-Ray Stadium Sports Drink", 10, 200));
        room18.unlockEastRoom();
        room18.lockEastRoom();

        // Zombie Room
        room19.setEastRoom(room18);
        room19.addItems(new Item("XRay Glasses", 500));
        room19.addItems(new Item("Zombie Eyes", 200));
        room19.addItems(new Food("Human Brain", 100, 150));
        room19.addEnemy(new Enemy("Zombie", 500, 30));
        room19.addEnemy(new Enemy("Zombie 2", 500, 30));
        room19.addEnemy(new Enemy("Zombie 3", 500, 30));
        room19.addEnemy(new Enemy("Zombie 4", 500, 30));
        room19.addItems(new Liquid("Zombie Blood", -30, 150));

        // Eden's Garden
        room20.setNorthRoom(room16);
        room20.addItems(new Item("Ancient Relic", 800));
        room20.addItems(new Item("Floral Bouquet", 500));
        room20.addItems(new Food("Ambrosia", 100, 200));
        room20.addItems(new Food("Golden Apple", 50, 150));
        room20.addEnemy(new Enemy("Samael", 1000, 200));
        room20.addItems(new Liquid("Eden's Garden Nectar", 30, 200));

        // The Room of Deception
        room21.setNorthRoom(room17);
        room21.addItems(new Item("Gold", 200));
        room21.addItems(new Item("Sword", 200));
        room21.addItems(new Food("Brownies", -300, 50));
        room21.addEnemy(new Enemy("Deceiver", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 2", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 3", 500, 30));
        room21.addEnemy(new Enemy("Deceiver 4", 500, 30));
        room21.addItems(new Liquid("Room of Deception Potion", 50, 250));

        return room1;
    }
}