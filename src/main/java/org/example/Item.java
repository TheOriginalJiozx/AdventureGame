package org.example;

public class Item
{
    private String name;
    private String shortName;

    public Item(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
