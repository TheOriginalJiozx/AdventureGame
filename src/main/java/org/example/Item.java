package org.example;

public class Item {
    private String name;
    private String shortName;
    private boolean taken;

    public Item(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
        this.taken = false;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}