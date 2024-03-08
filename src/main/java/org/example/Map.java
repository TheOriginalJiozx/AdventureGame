package org.example;

public class Map {
    private Room startingRoom;

    public Map(){
        createRooms();
    }

    private void createRooms() {
        Room room1 = new Room("Desert", "You find yourself in the heart of a vast desert, surrounded by towering sand dunes that seem to stretch into eternity. The scorching sun beats down relentlessly, casting shimmering waves of heat across the golden landscape. Mirage-like illusions dance on the horizon, teasing your senses with the promise of salvation.\n");
        Room room2 = new Room("Goblin Camp", "As you traverse through the desolate desert, the oppressive heat gradually gives way to a putrid stench that assaults your nostrils. You soon stumble upon a sprawling goblin encampment, its crude structures fashioned from bones and rotting flesh. The air is thick with tension, and sinister whispers echo through the camp, sending shivers down your spine.\n");
        Room room3 = new Room("Abandoned Mine Tunnels", "You venture into the depths of an abandoned mine, its dark tunnels twisting and turning like the veins of some ancient behemoth. The air is heavy with the metallic tang of ore and the musty scent of decay. Shadows dance eerily on the walls, playing tricks on your mind as you navigate the labyrinthine passages.\n");
        Room room4 = new Room("Temple Ruins", "Amidst the dense foliage of an overgrown jungle, you stumble upon the crumbling ruins of an ancient temple, its weathered stones bearing silent witness to the passage of time. Vines coil around intricate carvings, reclaiming the structure for nature's embrace. A sense of reverence hangs heavy in the air, tinged with a hint of foreboding.\n");
        Room room5 = new Room("Famous Treasure Chamber", "You enter a legendary treasure chamber, its opulent halls adorned with glittering jewels and precious artifacts from ages past. The air is thick with the scent of wealth, and the soft glow of gold illuminates your path. But beware, for with great riches come great dangers, and not all who seek fortune emerge unscathed.\n");
        Room room6 = new Room("Realm of Hell", "You find yourself in the heart of darkness, where the flames of damnation burn eternal and the screams of the tormented echo through the abyss. The air is thick with the acrid stench of sulfur, and the ground trembles beneath your feet as infernal creatures lurk in the shadows, hungering for your soul.\n");
        Room room7 = new Room("Manic Plains", "You stand upon a vast expanse of rolling plains, where the grasses sway in the gentle breeze like ocean waves. The sky stretches out endlessly above you, a canvas of shifting colors and swirling clouds. A sense of freedom fills your heart as you drink in the beauty of the untamed wilderness.\n");
        Room room8 = new Room("Coastal Cliffs", "You find yourself perched on the edge of towering cliffs that overlook the endless expanse of the sea. The salty breeze carries the cries of seagulls and the rhythmic crash of waves against the rocky shore. Below, jagged rocks jut out of the water like the teeth of some ancient leviathan, warning of the dangers that lie beneath the surface.\n");
        Room room9 = new Room("Elysian Fields", "You enter a realm of unimaginable beauty, where lush meadows stretch out as far as the eye can see and flowers bloom in a riot of color. The air is sweet with the scent of honeysuckle and lavender, and a gentle warmth suffuses your being as you bask in the embrace of paradise.\n");

        room1.setEastRoom(room2);
        room1.addItem(new Item("Golden Key", "Key"));
        room1.setSouthRoom(room4);
        room4.setNorthRoom(room1);
        room2.addItem(new Item("Pink Revovler", "Revolver"));
        room2.setWestRoom(room1);
        room2.setEastRoom(room3);
        room3.addItem(new Item("Putin's Bazooka", "Suicide"));
        room3.setWestRoom(room2);
        room3.setSouthRoom(room6);
        room4.addItem(new Item("H.C. Andersen's Book", "The Ugly Duckling"));
        room6.setNorthRoom(room3);
        room6.setSouthRoom(room9);
        room5.addItem(new Item("Saint Patrick's Gold", "Gold"));
        room9.setNorthRoom(room6);
        room9.setWestRoom(room8);
        room6.addItem(new Item("The Devil's Knife", "Knife"));
        room8.setEastRoom(room9);
        room8.setWestRoom(room7);
        room7.addItem(new Item("A Guitar made of Unicorns", "Unicorn Guitar"));
        room7.setEastRoom(room8);
        room7.setNorthRoom(room4);
        room8.addItem(new Item("Tarzan's Rope", "Rope"));
        room4.setSouthRoom(room7);
        room8.setNorthRoom(room5);
        room9.addItem(new Item("George Bush's Bomb", "Bomb"));

        startingRoom = room1;
    }

    public Room getStartingRoom(){
        return startingRoom;
    }
}