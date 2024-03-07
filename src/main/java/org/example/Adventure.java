package org.example;

public class Adventure {

    private boolean hasWall(Room room, Direction direction) {
        switch (direction) {
            case NORTH:
                return room.getNorthRoom() == null;
            case SOUTH:
                return room.getSouthRoom() == null;
            case EAST:
                return room.getEastRoom() == null;
            case WEST:
                return room.getWestRoom() == null;
            default:
                return false;
        }
    }

    public void helpUser(Room room) {
        System.out.println();
        room.helpUser();
    }

    public void lookAround(Room room) {
        System.out.println();
        room.lookAround();
        System.out.println();
    }
}