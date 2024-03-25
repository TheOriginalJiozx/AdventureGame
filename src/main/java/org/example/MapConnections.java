package org.example;

public class MapConnections {
    private boolean isBombTownUnlocked = false;
    private boolean isCoastUnlockedW = false;
    private boolean isCoastUnlockedE = false;
    private boolean isEdensGardenUnlocked = false;

    public void unlockBombTown() {
        isBombTownUnlocked = true;
    }

    public void unlockCoastW() {
        isCoastUnlockedW = true;
    }

    public void unlockCoastE() {
        isCoastUnlockedE = true;
    }

    public void unlockEdensGarden() {
        isEdensGardenUnlocked = true;
    }
    public void displayMap() {
        String L = "\uD83D\uDD12"; // Locked room emoji
        String U = "\uD83D\uDD13"; // Unlocked room emoji
        String horizontalLine = " _______________________________________________________________________________";

        String bombTownLock = isBombTownUnlocked ? U : L; // Use the Bomb Town unlock state
        String coastLockW = isCoastUnlockedW ? U : L; // Use the Coast unlock state
        String coastLockE = isCoastUnlockedE ? U : L; // Use the Coast unlock state
        String edensGardenLock = isEdensGardenUnlocked ? U : L; // Use the Coast unlock state

        // Display the map with room indicators
        System.out.println(horizontalLine +
                "\n|               |               |               |               |               |" +
                "\n|   Bomb Town  " + bombTownLock + "     Desert       Goblin Camp     Mine Tunnels    Gotham City  |" +
                "\n|               |               |               |               |               |" +
                "\n|______   ______|______   ______|_______________|______   ______|______   ______|" +
                "\n|               |               |               |               |               |" +
                "\n|   Jerusalem      Temple Ruins | King David's  |      Hell     |  Gold Mine    |" +
                "\n|               |               |     Room      |               |               |" +
                "\n|______   ______|______   ______|______" + L + "_______|_______________|______   ______|" +
                "\n|               |               |               |               |               |" +
                "\n|  The Room of  |    Manic     " + coastLockE + "     Coast    " + coastLockW + "     Cave      |    The Nile   |" +
                "\n|   Deception   |    Plains     |               |               |               |" +
                "\n|_______________|______   ______|______" + L + "_______|______   ______|______" + L + "_______|" +
                "\n|               |               |               |               |               |" +
                "\n|               |               |               |     Mars      |               |" +
                "\n|               |               |               |_______________|               |" +
                "\n|  Zombie Room    X-Ray Stadium |   Vice City   |               | Eden's Garden |" +
                "\n|               |              " + L + "              " + L + "   Clown Town " + edensGardenLock + "               |" +
                "\n|               |               |               |               |               |" +
                "\n|_______________|_______________|_______________|_______________|_______________|");
    }
}