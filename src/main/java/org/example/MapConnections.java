package org.example;

public class MapConnections {
    private boolean isBombTownUnlockedW = false;
    private boolean isCoastUnlockedW = false;
    private boolean isCoastUnlockedE = false;
    private boolean isCoastUnlockedN = false;
    private boolean isKingsRoomUnlocked = false;
    private boolean isEdensGardenUnlockedE = false;
    private boolean isEdensGardenUnlockedS = false;
    private boolean isClownTownUnlockedE = false;
    private boolean isViceCityUnlockedE = false;

    public boolean isBombTownUnlockedW() {
        return isBombTownUnlockedW;
    }

    public boolean isCoastUnlockedW() {
        return isCoastUnlockedW;
    }

    public boolean isCoastUnlockedE() {
        return isCoastUnlockedE;
    }

    public boolean isCoastUnlockedN() {
        return isCoastUnlockedN;
    }

    public boolean isKingsRoomUnlocked() {
        return isKingsRoomUnlocked;
    }

    public boolean isEdensGardenUnlockedE() {
        return isEdensGardenUnlockedE;
    }

    public boolean isEdensGardenUnlockedS() {
        return isEdensGardenUnlockedS;
    }

    public boolean isClownTownUnlockedE() {
        return isClownTownUnlockedE;
    }

    public boolean isViceCityUnlockedE() {
        return isViceCityUnlockedE;
    }

    public void unlockBombTownW() {
        isBombTownUnlockedW = true;
    }

    public void unlockCoastW() {
        isCoastUnlockedW = true;
    }

    public void unlockCoastE() {
        isCoastUnlockedE = true;
    }

    public void unlockCoastN() {
        isCoastUnlockedN = true;
    }

    public void unlockKingsRoom() {
        isKingsRoomUnlocked = true;
    }

    public void unlockEdensGardenE() {
        isEdensGardenUnlockedE = true;
    }

    public void unlockEdensGardenS() {
        isEdensGardenUnlockedS = true;
    }

    public void unlockClownTownE() {
        isClownTownUnlockedE = true;
    }

    public void unlockViceCityE() {
        isViceCityUnlockedE = true;
    }

    public void displayMap() {
        String L = "\uD83D\uDD12";
        String U = "\uD83D\uDD13";
        String horizontalLine = " _______________________________________________________________________________";

        String bombTownLockW = isBombTownUnlockedW ? U : L;
        String coastLockW = isCoastUnlockedW ? U : L;
        String coastLockE = isCoastUnlockedE ? U : L;
        String coastLockN = isCoastUnlockedN ? U : L;
        String kingsRoomLock = isKingsRoomUnlocked ? U : L;
        String edensGardenLockE = isEdensGardenUnlockedE ? U : L;
        String edensGardenLockS = isEdensGardenUnlockedS ? U : L;
        String clownTownLockE = isClownTownUnlockedE ? U : L;
        String viceCityLockE = isViceCityUnlockedE ? U : L;

        System.out.println(horizontalLine +
                "\n|               |               |               |               |               |" +
                "\n|   Bomb Town  " + bombTownLockW + "     Desert       Goblin Camp     Mine Tunnels    Gotham City  |" +
                "\n|               |               |               |               |               |" +
                "\n|______   ______|______   ______|_______________|______   ______|______   ______|" +
                "\n|               |               |               |               |               |" +
                "\n|   Jerusalem      Temple Ruins | King David's  |      Hell     |  Gold Mine    |" +
                "\n|               |               |     Room      |               |               |" +
                "\n|______   ______|______   ______|______" + kingsRoomLock + "_______|_______________|______   ______|" +
                "\n|               |               |               |               |               |" +
                "\n|  The Room of  |    Manic     " + coastLockE + "     Coast    " + coastLockW + "     Cave      |    The Nile   |" +
                "\n|   Deception   |    Plains     |               |               |               |" +
                "\n|_______________|______   ______|______" + coastLockN + "_______|______   ______|______" + edensGardenLockS + "_______|" +
                "\n|               |               |               |               |               |" +
                "\n|               |               |               |     Mars      |               |" +
                "\n|               |               |               |_______________|               |" +
                "\n|  Zombie Room    X-Ray Stadium |   Vice City   |               | Eden's Garden |" +
                "\n|               |              " + viceCityLockE + "              " + clownTownLockE + "   Clown Town " + edensGardenLockE + "               |" +
                "\n|               |               |               |               |               |" +
                "\n|_______________|_______________|_______________|_______________|_______________|");
    }
}