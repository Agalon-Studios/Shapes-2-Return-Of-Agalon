package com.github.agalonstudios;

/**
 * Created by spr on 3/14/17.
 */
public class EnemySpawnPointItem extends DungeonRoomPlanItem {
    private int m_enemyCount;
    private int m_spawnRadius;

    public EnemySpawnPointItem(int count, int radius) {
        m_enemyCount = count;
        m_spawnRadius = radius;
    }

    @Override
    public char toChar() {
        return 'E';
    }
}
