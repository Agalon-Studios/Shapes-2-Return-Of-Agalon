package com.github.agalonstudios;

public class DoorItem extends DungeonRoomPlanItem {
    private int m_side; // north south east west

    public DoorItem(int dir) {
        m_side = dir;

    }



    public int getDir() {
        return m_side;
    }

    public char toChar() {
        return 'D';
    }
}