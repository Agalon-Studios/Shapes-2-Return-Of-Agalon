package com.github.agalonstudios;

public class ChestItem extends DungeonRoomPlanItem {
    private int m_value;

    public ChestItem(int value, Theme theme) {
        m_value = value;
    }

    public char toChar() {
        return 'C';
    }
}