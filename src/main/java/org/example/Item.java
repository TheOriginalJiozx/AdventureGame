package org.example;
public class Item {
    private String name;
    private String shortName;
    private int maxCarry = 40000;
    private int weight;

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.shortName = generateShortName(name);
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getMaxCarry() {
        return maxCarry;
    }

    public int getWeight() {
        return weight;
    }

    private String generateShortName(String longName) {
        StringBuilder shortNameBuilder = new StringBuilder();
        String[] words = longName.split(" ");
        if (words.length > 0) {
            for (int i = words.length - 1; i >= 0; i--) {
                String word = words[i];
                if (!word.equalsIgnoreCase("of") && !word.equalsIgnoreCase("the") &&
                        !word.equalsIgnoreCase("and") && !word.equalsIgnoreCase("or")) {
                    shortNameBuilder.append(Character.toUpperCase(word.charAt(0)));
                    if (word.length() > 1) {
                        shortNameBuilder.append(word.substring(1).toLowerCase());
                    }
                    break;
                }
            }
        } else {
            shortNameBuilder.append(longName.toUpperCase());
        }
        return shortNameBuilder.toString();
    }
}