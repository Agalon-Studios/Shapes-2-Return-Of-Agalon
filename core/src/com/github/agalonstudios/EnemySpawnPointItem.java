package com.github.agalonstudios;

/**
 * Created by spr on 3/14/17.
 */
public class EnemySpawnPointItem extends DungeonRoomPlanItem {
    private int m_enemyCount;
    private int m_spawnRadius;
    private EnemyType m_enemyType;

    public EnemySpawnPointItem(int count, int radius, EnemyType enemyType) {
        m_enemyCount = count;
        m_spawnRadius = radius;
        m_enemyType = enemyType;
    }

    @Override
    public char toChar() {
        return 'E';
    }

    public int getRadius() {
        return m_spawnRadius;
    }

    public int getCount() {
        return m_enemyCount;
    }

    public EnemyType getEnemyType() {
        return m_enemyType;
    }
}
