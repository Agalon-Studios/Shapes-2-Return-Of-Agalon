package com.github.agalonstudios;

public class DoorItem extends DungeonRoomPlanItem {
    private int side; // north south east west

    public DoorItem(int dir) {
        side = dir;
    }

    public int getDir() {
        return side;
    }

    public char toChar() {
        return 'D';
    }
}